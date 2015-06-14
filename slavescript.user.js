// ==UserScript== 
// @name Monster Minigame Slave Script
// @author /u/ags131
// @version 1.08
// @namespace https://github.com/ags131/steamMinigameSlaveScript
// @description A script that spawns slave instances of the Steam Monster Minigame
// @match *://steamcommunity.com/minigame/towerattack*
// @match *://steamcommunity.com//minigame/towerattack*
// @grant none
// @updateURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// @downloadURL https://raw.githubusercontent.com/ags131/steamMinigameSlaveScript/master/slavescript.user.js
// ==/UserScript==

var slaveWindowAutoStart = true; // Start slaves when master loads

var slaveWindowCount = 5; // Adjust this according to your computers capabilities, more slaves means more ram and CPU. 
var slaveWindowUICleanup = true; // Hide all UI and disable rendering for slaves. This will help on CPU and possibly RAM usage

var slaveWindowPeriodicRestart = true; // Periodically restarts slaves in attempts to free memory
var slaveWindowPeriodicRestartInterval = 15 * 60 * 1000;  // Period to restart slaves (In milliseconds)

var masterWindowPeriodicRestart = true; // Periodically restarts master in attempts to free memory
var masterWindowPeriodicRestartInterval = 2 * 60 * 1000;  // Period to restart master (In milliseconds)

var slaveWindowManuallyKilled = false; // Flag for kill slaves is ran to prevent periodic restart;

if(masterWindowPeriodicRestart && slaveWindowAutoStart) 
	slaveWindowPeriodicRestart = false; // Disable slaveWindowPeriodicRestart if masterWindowPeriodicRestart && slaveWindowAutoStart

if(location.search.match(/slave/))
	runSlave()
else
	runMaster()

function runMaster()
{
	window.unload = function(){ killAllSlaves() }

	var slavesList = window.slavesList = [];
	
	function spawnSlave(num){
		var params = 'left=0, top='+(num*100)+', width=220, height=100';
		var slave = window.open("http://steamcommunity.com/minigame/towerattack/?slave",'slave'+num, params)
		if(num >= slavesList.length)
			slavesList.push(slave);
		$J('.slaveWindowCount').text(slavesList.length)
	}
	
	function spawnSlaves(){
		slaveWindowManuallyKilled = false
		cnt = slaveWindowCount;
		for(var i=0;i<cnt;i++)
			setTimeout(spawnSlave.bind(window,i),i * 3000)
	}
	
	function killAllSlaves(){
		slaveWindowManuallyKilled = true;
		while(slavesList.length) {
			var toKill = slavesList.pop();
			
			if(toKill)
				toKill.close();
		}
		$J('.slaveWindowCount').text(slavesList.length)
	}
	
	var startupCheckInter = setInterval(function(){
		if(g_Minigame.m_CurrentScene.m_bRunning)
		{
			clearInterval(startupCheckInter);
			if(slaveWindowAutoStart)
				spawnSlaves()		
		}
	},1000)

	if(masterWindowPeriodicRestart)
		setTimeout(function(){ location.reload() }, masterWindowPeriodicRestartInterval)
	
	var counterStyle = {
		'position': 'relative',
		'font-weight': 'bold',
		'top': '25px',
		'margin-left': '10px',
		'color': '#FF8585'
	};
 			 	
	var spacerStyle = {
		'margin-left': '100px'
	};

	var tgt = $J('.game_options .toggle_music_btn:first');
	var spawnSlavesBtn = $J('<span>').addClass('toggle_music_btn').insertAfter(tgt).click(spawnSlaves).text('Spawn Slaves').css(spacerStyle);
	var killSlavesBtn = $J('<span>').addClass('toggle_music_btn').insertAfter(spawnSlavesBtn).click(killAllSlaves).text('Kill Slaves');
	$J('<span id="slaveCounter">Slaves: <span class="slaveWindowCount">0</span></span>').insertAfter(killSlavesBtn).css(counterStyle);

 	$J('#slaveCounter').css(counterStyle);
}
function runSlave()
{
	if(slaveWindowUICleanup){
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

	if(slaveWindowPeriodicRestart) {
		var resetInterval = setInterval(function () {
			// Only refresh if we're not on a boss / Treasure mob
			var target = getTarget();
			if(target && target.m_data.type != 2 && target.m_data.type != 4 ){
				clearInterval(resetInterval); // Shouldn't need this but meh.
				window.location.href = "./?slave";
			}
		}, slaveWindowPeriodicRestartInterval);
	}
}