// ==UserScript== 
// @name Monster Minigame Slave Script
// @author /u/ags131
// @version 1.06
// @namespace https://github.com/ags131/steamMinigameSlaveScript
// @description A script that spawns slave instances of the Steam Monster Minigame
// @match *://steamcommunity.com/minigame/towerattack*
// @match *://steamcommunity.com//minigame/towerattack*
// @grant none
// @updateURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// @downloadURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// ==/UserScript==

var autoStartSlaves = true; // Start slaves when master loads

var slaveCount = 5; // Adjust this according to your computers capabilities, more slaves means more ram and CPU. 
var cleanupSlave = true; // Hide all UI and disable rendering for slaves. This will help on CPU and possibly RAM usage

var restartSlaves = true; // Periodically restarts slaves in attempts to free memory
var slaveRestartInterval = 15 * 60 * 1000;  // Period to restart slaves (In milliseconds)

var restartMaster = true; // Periodically restarts master in attempts to free memory
var masterRestartInterval = 2 * 60 * 1000;  // Period to restart master (In milliseconds)

var manualKilled = false;

if(restartMaster && autoStartSlaves) 
	restartSlaves = false; // Disable restartSlaves if restartMaster && autoStartSlaves

if(location.search.match(/slave/))
	runSlave()
else
	runMaster()

function runMaster()
{
	window.unload = function(){ killAllSlaves() }
	var slaves = window.slaves = [];
	function spawnSlave(num){
		var slaveheight = screen.height / 10;
		var params = 'left=0, top='+(num*100)+', width=220, height=100';
		var slave = window.open("http://steamcommunity.com/minigame/towerattack/?slave",'slave'+num, params)
		if(num >= slaves.length)
			slaves.push(slave);
		$J('.slaveCount').text(slaves.length)
	}
	
	function spawnSlaves(){
		manualKilled = false
		cnt = slaveCount;
		for(var i=0;i<cnt;i++)
			setTimeout(spawnSlave.bind(window,i),i * 3000)
	}
	
	function killAllSlaves(){
		manualKilled = true;
		while(slaves.length)
			slaves.pop().close()
		$J('.slaveCount').text(slaves.length)
	}
	
	var startupCheckInter = setInterval(function(){
		if(g_Minigame.m_CurrentScene.m_bRunning)
		{
			clearInterval(startupCheckInter);
			if(autoStartSlaves)
				spawnSlaves()		
		}
	},1000)
	
	if(restartSlaves)
		setInterval(function(){ 
			if(!manualKilled)
				spawnSlaves()
		},slaveRestartInterval)

	if(restartMaster)
		setTimeout(function(){ location.reload() }, masterRestartInterval)
	
	var tgt = $J('.game_options .toggle_music_btn:first')
	$J('<span>').addClass('toggle_music_btn').insertAfter(tgt).click(killAllSlaves).text('Kill Slaves')
	$J('<span>').addClass('toggle_music_btn').insertAfter(tgt).click(spawnSlaves).text('Spawn Slaves')
}
function runSlave()
{
	if(cleanupSlave){
		var cleanupPageInter = setInterval(function(){
			if(window.CUI || g_Minigame.m_CurrentScene.m_bRunning)
			{
				clearInterval(cleanupPageInter);
				$J('body > *').hide()
				var cont = $J('body');
				$J('<div>').css({
					'padding-top': '20px',
					'font-family': '"Press Start 2P"',
					'font-size': '32pt'
				})
				.text('Slave')
				.appendTo(cont);
				g_Minigame.Renderer.render = function(){} // Disable rendering. Completely.
			}
		},1000)
	}
}