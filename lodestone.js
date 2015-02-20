/*global */
/*jslint nomen: true */

var _       = require('underscore');
var request = require('request');
var cheerio = require('cheerio');
// var async  = require('async');
// var MongoClient = require('mongodb').MongoClient;
var url = require('url');

var assert = require("assert");

var jobs_png = {};

jobs_png['d39804e8810aa3d8e467b7a476d01965510c5d18.png'] = 'Archer';
jobs_png['59fde9fca303490477962039f6cd0d0101caeabe.png'] = 'Arcanist';
jobs_png['6157497a98f55a73af4c277f383d0a23551e9e98.png'] = 'Conjurer';
jobs_png['924ded09293b2a04c4cd662afbf7cda7b0576888.png'] = 'Lancer';
jobs_png['5ca476c2166b399e3ec92e8008544fdbea75b6a2.png'] = 'Marauder';
jobs_png['ec5d264e53ea7749d916d7d8bc235ec9c8bb7b51.png'] = 'Gladiator';
jobs_png['9fe08b7e2827a51fc216e6407646ffba716a44b8.png'] = 'Pugilist';
jobs_png['e2a98c81ca279607fc1706e5e1b11bc08cac2578.png'] = 'Thaumaturge';

jobs_png['7a72ef2dc1918f56e573dd28cffcec7e33a595df.png'] = 'Bard';
jobs_png['ee5788ae748ff28a503fecbec2a523dbc6875298.png'] = 'Scholar';
jobs_png['2c38a1b928c88fd20bcc74fe0b4d9ba0a8f56f67.png'] = 'Summoner';
jobs_png['c460e288d5db83ebc90d0654bee6d0d0a0a9582d.png'] = 'White Mage';
jobs_png['36ce9c4cc01581d4f900102cd51e09c60c3876a6.png'] = 'Dragoon';
jobs_png['2de279517a8de132f2faad4986a507ed728a067f.png'] = 'Warrior';
jobs_png['626a1a0927f7d2510a92558e8032831264110f26.png'] = 'Paladin';
jobs_png['8873ffdf5f7c80770bc40f5b82ae1be6fa1f8305.png'] = 'Monk';
jobs_png['1d4a1cf6021705ee62c5b5dfc100781f0f272623.png'] = 'Rogue';
jobs_png['98d95dec1f321f111439032b64bc42b98c063f1b.png'] = 'Black Mage';

jobs_png['343bce834add76f5d714f33154d0c70e99d495a3.png'] = 'Alchemist';
jobs_png['aab4391a4a5633684e1b93174713c1c52f791930.png'] = 'Armorer';
jobs_png['6e0223f41a926eab7e6bc42af7dd29b915999db1.png'] = 'Blacksmith';
jobs_png['d41cb306af74bb5407bc74fa865e9207a5ce4899.png'] = 'Carpenter';
jobs_png['86f1875ebc31f88eb917283665be128689a9669b.png'] = 'Culinarian';
jobs_png['605aa74019178eef7d8ba790b3db10ac8e9cd4ca.png'] = 'Goldsmith';
jobs_png['f358b50ff0a1b1dcb67490ba8f4c480e01e4edd7.png'] = 'Leatherworker';
jobs_png['131b914b2be4563ec76b870d1fa44aa8da0f1ee6.png'] = 'Weaver';

jobs_png['937d3313d9d7ef491319c38a4d4cde4035eb1ab3.png'] = 'Botanist';
jobs_png['289dbc0b50956ce10a2195a75a22b500a648284e.png'] = 'Fisher';
jobs_png['8e82259fcd979378632cde0c9767c15dba3790af.png'] = 'Miner';

var wpn_type_names = {"Gladiator's Arm"              : "main_hand",
                      "Pugilist's Arm"               : "main_hand",
                      "Rogue's Arms"               : "main_hand",
                      "Marauder's Arm"               : "main_hand",
                      "Lancer's Arm"                 : "main_hand",
                      "Archer's Arm"                 : "main_hand",
                      "One-handed Conjurer's Arm"    : "main_hand",
                      "Two-handed Conjurer's Arm"    : "main_hand",
                      "One-handed Thaumaturge's Arm" : "main_hand",
                      "Two-handed Thaumaturge's Arm" : "main_hand",
                      "Arcanist's Grimoire"          : "main_hand",

                      "Shield"                       : "off_hand",
                      "Head"                         : "head",
                      "Body"                         : "body",
                      "Hands"                        : "hands",
                      "Waist"                        : "waist",
                      "Legs"                         : "legs",
                      "Feet"                         : "feet",

                      "Necklace"                     : "neck",
                      "Earrings"                     : "ears",
                      "Bracelets"                    : "wrists",
                      "Ring"                         : "ring",
                      "Soul Crystal"                 : "soul_crystal",

                      //DoH
                      "Carpenter's Primary Tool"         : "main_hand",
                      "Carpenter's Secondary Tool"       : "off_hand",

                      "Blacksmith's Primary Tool"        : "main_hand",
                      "Blacksmith's Secondary Tool"      : "off_hand",

                      "Armorer's Primary Tool"           : "main_hand",
                      "Armorer's Secondary Tool"         : "off_hand",

                      "Goldsmith's Primary Tool"         : "main_hand",
                      "Goldsmith's Secondary Tool"       : "off_hand",

                      "Leatherworker's Primary Tool"     : "main_hand",
                      "Leatherworker's Secondary Tool"   : "off_hand",

                      "Weaver's Primary Tool"            : "main_hand",
                      "Weaver's Secondary Tool"          : "off_hand",

                      "Alchemist's Primary Tool"         : "main_hand",
                      "Alchemist's Secondary Tool"       : "off_hand",

                      "Culinarian's Primary Tool"        : "main_hand",
                      "Culinarian's Secondary Tool"      : "off_hand",

                      //DoL

                      "Miner's Primary Tool"             : "main_hand",
                      "Miner's Secondary Tool"           : "off_hand",

                      "Botanist's Primary Tool"          : "main_hand",
                      "Botanist's Secondary Tool"        : "off_hand",

                      "Fisher's Primary Tool"            : "main_hand",
                      };

function getItemSlot(itemType) {

    'use strict';

    return wpn_type_names[itemType];

}

function getCannotEquipGearTo(text, itemType) {

    'use strict';

    var wpn_type_names = {"Gladiator's Arm"              : [],
                          "Pugilist's Arm"               : ['Off Hand'],
                          "Rogue's Arms"                 : ['Off Hand'],
                          "Marauder's Arm"               : ['Off Hand'],
                          "Lancer's Arm"                 : ['Off Hand'],
                          "Archer's Arm"                 : ['Off Hand'],
                          "One-handed Conjurer's Arm"    : [],
                          "Two-handed Conjurer's Arm"    : ['Off Hand'],
                          "One-handed Thaumaturge's Arm" : [],
                          "Two-handed Thaumaturge's Arm" : ['Off Hand'],
                          "Arcanist's Grimoire"          : ['Off Hand'],

                          "Shield"                       : [],
                          "Head"                         : [],
                          "Body"                         : [],
                          "Hands"                        : [],
                          "Waist"                        : [],
                          "Legs"                         : [],
                          "Feet"                         : [],
                          "Necklace"                     : [],
                          "Earrings"                     : [],
                          "Bracelets"                    : [],
                          "Ring"                         : [],
                          "Soul Crystal"                 : [],

                          //DoH
                          "Carpenter's Primary Tool"         : [],
                          "Carpenter's Secondary Tool"       : [],

                          "Blacksmith's Primary Tool"        : [],
                          "Blacksmith's Secondary Tool"      : [],

                          "Armorer's Primary Tool"           : [],
                          "Armorer's Secondary Tool"         : [],

                          "Goldsmith's Primary Tool"         : [],
                          "Goldsmith's Secondary Tool"       : [],

                          "Leatherworker's Primary Tool"     : [],
                          "Leatherworker's Secondary Tool"   : [],

                          "Weaver's Primary Tool"            : [],
                          "Weaver's Secondary Tool"          : [],

                          "Alchemist's Primary Tool"         : [],
                          "Alchemist's Secondary Tool"       : [],

                          "Culinarian's Primary Tool"        : [],
                          "Culinarian's Secondary Tool"      : [],

                          //DoL
                          "Miner's Primary Tool"             : [],
                          "Miner's Secondary Tool"           : [],

                          "Botanist's Primary Tool"          : [],
                          "Botanist's Secondary Tool"        : [],

                          "Fisher's Primary Tool"            : ['Off Hand'],
            },



        adds = [];

    if (text.match(/Cannot equip gear to/g) !== null) {

        adds = text
            .match(/Cannot equip gear to/g)[0]
                .replace('Cannot equip gear to ', '')
                    .replace(/,/g, '').replace(/\./g, '')
                        .replace(/\sand\s/g, ' ')
                            .replace(/\sor\s/g, ' ')
                                .split(' ');
    }

    return _.reduce(adds, function (memo, add) {

        memo.push(add.charAt(0).toUpperCase() + add.slice(1));
        return memo;
    }, _.clone(wpn_type_names[itemType] || []));
}

function calcIlv(items) {

    'use strict';

    var res = (_.reduce(items, function (memo, item) {

        if (item === null) {
            return memo;
        }

        var num = (item.cannotEquipGearTo.length + 1) * item.ilv;

        if (item.type === "Soul Crystal") {
            num = 0;
        }

        return memo + num;

    }, 0) / 13);

    return Math.floor(res);

}

var _scrape = function (body, callback) {

    'use strict';

    var character = {},
        $ = cheerio.load(body),
        png_classe_immagine = $('div#class_info img').attr('src')
            .replace('http://img.finalfantasyxiv.com/lds/pc/global/images/class/24/', '')
                .replace(/\?[0-9]*/g, '');

    character.current_job       = jobs_png[png_classe_immagine];
    character.current_job_level = parseInt($('.level').text().replace('LEVEL ', ''), 10);

    // character.last_updated  = new Date();
    character.name          = $('.area_footer.player_name_txt a').text().trim();
    character.id            = $('.area_footer.player_name_txt a').attr('href').replace('/lodestone/character/', '').replace('/', '');
    character.server        = $('.area_footer.player_name_txt span').text().trim().replace('(', '').replace(')', '');

    //FC
    if ($('.chara_profile_list a.txt_yellow').length !== 0) {

        character.fc        = {};
        character.fc.id     = $('.chara_profile_list a.txt_yellow').attr('href').replace('/lodestone/freecompany/', '').replace('/', '');
        character.fc.name   = $('.chara_profile_list a.txt_yellow').text();
        character.fc.server = character.server;
    }

    character.class = character.class || {};

    $(".base_inner td[class^='ic_class']").each(function () {

        if ($(this).text() === '') {
            return;
        }

        character.class[$(this).text().toLowerCase()]       = {};
        character.class[$(this).text().toLowerCase()].name  = $(this).text();
        character.class[$(this).text().toLowerCase()].level = parseInt($(this).next().text(), 10) || 0;
    });

    character.equip = {
        main_hand : null,
        off_hand : null,
        head : null,
        body : null,
        hands : null,
        waist : null,
        legs : null,
        feet : null,

        neck : null,
        ears : null,
        wrists : null,

        ring1 : null,
        ring2 : null,

        soul_crystal : null,
    };

    $('.popup_w412_body_gold').each(function () {//ogni oggetto

        var item = {};

        item.name = $(this).find('.item_name').text();
        item.ilv  = parseInt($(this).find('.pt3').text().replace('Item Level ', ''), 10);
        item.type = $(this).find('.name_area.clearfix .category_name').text().trim();

        item.id   = $(this).find('.bt_db_item_detail a').attr('href').replace('/lodestone/playguide/db/item/', '').replace('/', '');


        if (getItemSlot(item.type) === 'ring') {

            if (character.equip.ring1 === null) {
                character.equip.ring1 = item;
            } else {
                character.equip.ring2 = item;
            }
        } else {
            character.equip[getItemSlot(item.type)] = item;
        }

        //*******
        item.cannotEquipGearTo = getCannotEquipGearTo($(this).find('.ml12.mb10').text(), item.type);

    });

    character.ilv = calcIlv(character.equip);

    _.each(character.equip, function (item) {

        if (item !== null) {
            delete item.cannotEquipGearTo;
        }

    });

    callback(null, character);
};

var getChar = function (character_id, callback) {

    'use strict';

    request('http://na.finalfantasyxiv.com/lodestone/character/' + character_id + '/',
                  function (error, response, body) {

            if (!error && response.statusCode === 200) {

                _scrape(body, function (err, char) {

                    callback(err, char);
                });

            } else {

                callback(error, null);
            }

            response.resume();
        });

};

exports._scrape = _scrape;
exports.getChar = getChar;
