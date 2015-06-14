// ==UserScript== 
// @name Monster Minigame Slave Script
// @author /u/ags131
// @version 1.03
// @namespace https://github.com/ags131/steamMinigameSlaveScript
// @description A script that spawns slave instances of the Steam Monster Minigame
// @match *://steamcommunity.com/minigame/towerattack*
// @match *://steamcommunity.com//minigame/towerattack*
// @grant none
// @updateURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// @downloadURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// ==/UserScript==

var autoStartSlaves = true; // Start slaves when master loads
var slaveCount = 10; // Adjust this according to your computers capabilities, more slaves means more ram and CPU. 
var cleanupSlave = true; // Hide all UI and disable rendering for slaves. This will help on CPU and possibly RAM usage
var restartSlaves = true; // Periodically restarts slaves in attempts to free memory
var slaveRestartInterval = 5 * 60 * 1000;  // Period to restart slaves (In milliseconds)

if(location.search.match(/slave/))
	runSlave()
else
	runMaster()

function runMaster()
{
	window.unload = function(){ killAllSlaves() }
	var slaves = window.slaves = [];
	function spawnSlave(){
		var num = slaves.length
		var slaveheight = screen.height / 10;
		var params = 'left=0, top='+(num*100)+', width=220, height=100';
		var slave = window.open("http://steamcommunity.com/minigame/towerattack/?slave",'slave'+num, params)
		slaves.push(slave);
		$J('.slaveCount').text(slaves.length)
	}
	
	function spawnSlaves(){
		cnt = slaveCount;
		for(var i=0;i<cnt;i++)
			spawnSlave()
	}
	
	function killAllSlaves(){
		while(slaves.length)
			slaves.pop().close()
		$J('.slaveCount').text(slaves.length)
	}
	
	if(autoStartSlaves)
		spawnSlaves()
	
	if(restartSlaves)
	{
		setInterval(function(){
			var cnt = slaves.length;
			killAllSlaves()
			spawnSlaves(cnt)
		},slaveRestartInterval)
	}
	
	var cont = $J('<div>').addClass('slaveManager');
	cont.css({
		position: 'absolute',
		'z-index': 12,
		bottom: '20px',
		left: '30px'
	})
	var btnStyle = {
		border: 'none',
		padding: '5px',
		'border-radius': '10px'
	}
	$J('<button>').css(btnStyle).appendTo(cont).click(spawnSlaves).text('Spawn Slaves')
	$J('<button>').css(btnStyle).appendTo(cont).click(killAllSlaves).text('Kill All Slaves')
	cont.append('<span>Slaves: <span class="slaveCount"></span></span>')
	cont.appendTo($J('#uicontainer'))
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