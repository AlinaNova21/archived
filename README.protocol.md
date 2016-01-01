## General format:
```
portal -> host
Byte - Use
00 - magic number (0x55 for regular response, 0x56 for tag event)
01 - Payload size
Payload
Checksum
Padding to 32 bytes

host -> portal
Byte - Use
00 - Magic number (0x55)
01 - Payload size
02 - Command
03 - Message counter (each message sent have a incermenting number, this number is used to find corresponding answer)
Payload
Checksum
Padding to 32 bytes
```

## Commands :
```
c0 c2 c3 c6 c7 c8 : leds color control
b0 b1 b3 : initialisation, seed (tea), challenge (tea)
d2, d3, d4 : NFC read pages, write page, get tag model (tea)
```

"c" messages syntaxe taken from https://github.com/woodenphone/lego_dimensions_protocol

"b" and "d" messages syntaxe are from https://github.com/ags131/node-ld 

I take NO CREDIT for this protocol analysis, all the hard stuff has been done by these peoples !

### c0 - Immediately switch pad(s) to a value
```
Byte: use
0: Always 0x55
1: Payload size
2: command cont
3: Message counter
4: Pad to change 0=all, 1=center, 2=left, 3=right,
5: Red value
6: Green value
7: Blue Value
8: Checksum
9-31: padding, 0x00
```

### c2 - Immediately change the colour of one or all pad(s), fade and flash available
```
Byte: use
0: Always 0x55
1: 0x08 Payload size == 8
2: 0xc2 Command
3: Message counter
4: Pad all=0 c=1 l=2 r=3
5: Pulse time - needs more investigation
6: Pulse count - 0x00 is forever, odd leaves on new colour, even leaves on old colour. 0xc8 and above is forever.
8: green
9: blue
10: Checksum
11-31: Padding

Pulse count byte:
0x00 - 000 - is forever
0x15 - 021 - stops, stays at new colour
0x16 - 022 - stops, stays at old colour
0x17 - 023 - stops, stays at new colour
0xc6 - 198 - stops, returning to original colour
0xc7 - 199 - stops, remaining at new colour
0xc8 - 200 - is forever
0xe0 - 224 - is forever
0xef - 239 - is forever
0xfa - 250 - is forever
0xfe - 254 - is forever
0xff - 255 - is forever

Pulse time byte:
These values are 'by-eye'
hex  - dec - description
0x00 - 000 - Immediate change to new colour
0x01 - 001 - fast
0x04 - 004 - noticibly slower
0x15 - 021 - about 1 per second
0x20 - 032 - about 1 per 2secs
0xff - 255 - still pulses but very slow
```

### c3 - set 1 or all pad(s) to a colour with variable flash rates
```
Flashes between old and new colour
Does not appear to synchronise flashes
Byte: use
00: Always 0x55
01: 0x09 Payload size == 9
02: 0xc3 Command
03: Message counter?
04: pad 0:all, 1:center, 2:left 3:right
05: On pulse length, higher is longer
06: Off pulse length, higher is longer
07: Number of pulses, 0xff is forever
08: red
09: green
10: blue
11: checksum
12-31: padding
```

### c6 - Fade pad(s) to value(s)
```
Byte: use
00: Always 0x55
01: Payload size == 20
02: 0xc6 Command
03: Message counter
04: c:enable 0x00:disable, other:enable
05: c:Transition time, 0x00 is immediate
06: c:Pulse count, 0x00 does not change pad
07: c:red
08: c:green
09: c:blue
10: l:enable 0x00:disable, other:enable
11: l:Transition time, 0x00 is immediate
12: l:Pulse count, 0x00 does not change pad
13: l:red
14: l:green
15: l:blue
16: r:enable 0x00:disable, other:enable
17: r:Transition time, 0x00 is immediate
18: r:Pulse count, 0x00 does not change pad
19: r:red
20: r:green
21: r:blue
22: Checksum
23-32: padding
```

### c7 - Flash all 3 pads with individual colours and rates, either change to new or return to old based on pulse count
```
Byte: use
00: Always 0x55
01: 0x17 Payload size == 23
02: 0xc7 Command
03: Message counter?
04: c: 0x00 disables command for pad, other values enable
05: c: On pulse length - 0x00 is almost impersceptible,  0xff is ~10 seconds
06: c: Off pulse length - 0x00 is almost impersceptible, 0xff is ~10 seconds
07: c: Number of flashes - odd value leaves pad in new colour, even leaves pad in old, except for 0x00, which changes to new. Values above 0xc6 dont stop.
08: c: red
09: c: green
10: c: blue
11: l: 0x00 disables command for pad, other values enable
12: l: On pulse length - 0x00 is almost impersceptible,  0xff is ~10 seconds
13: l: Off pulse length - 0x00 is almost impersceptible, 0xff is ~10 seconds
14: l: Number of flashes - odd value leaves pad in new colour, even leaves pad in old, except for 0x00, which changes to new. Values above 0xc6 dont stop.
15: l: red
16: l: green
17: l: blue
18: r: 0x00 disables command for pad, other values enable
19: r: On pulse length - 0x00 is almost impersceptible,  0xff is ~10 seconds
20: r: Off pulse length - 0x00 is almost impersceptible, 0xff is ~10 seconds
21: r: Number of flashes - odd value leaves pad in new colour, even leaves pad in old, except for 0x00, which changes to new. Values above 0xc6 dont stop.
22: r: red
23: r: green
24: r: blue
25: checksum
26-31: padding

Pulse count byte:
Stops flashing:
0x7b
0x80
0xa0
0xaa
0xbb
0xbf
0xc0
0xc3
0xc5
0xc6 - 198
Continues flashing forever:
0xc7 -199
0xc9
0xcc
0xdd
0xf0
0xff -255
```

### c8 Immediately switch pad(s) to set of colours
```
Byte: use
00: Always 0x55
01: 0x0e Payload size == 14
02: 0xc8 Command
03 Message counter
04: c:enable - 0x00 disables the command for this pad, other values enable
05: c:red
06: c:green
07: c:blue
08: l:enable - 0x00 disables the command for this pad, other values enable
09: l:red
10: l:green
11: l:blue
12: r:enable - 0x00 disables the command for this pad, other values enable
13: r:red
14: r:green
15: r:blue
16: Checksum
17-31: Padding
```

### b0 : initialisation of the portal
```
The portal will do nothing without this first message:
55: Always 0x55
0F: Payload size
B0: 0xb0 Command
01: Message counter
28: "("
63: "c"
29: ")"
20: " "
4C: "L"
45: "E"
47: "G"
4F: "O"
20: " "
32: "2"
30: "0"
31: "1"
34: "4"
F7: Checksum
```

### b1 : seed PRNG of the portal (?) TEA crypted
```
host to portal :
55: Always 0x55
0A: Payload size
B1: 0xb1 Command
03: Message counter
AA 6F C8 CD 21 1E F8 CE: TEA crypted Payload
C6: Checksum

Portal decrypt the message :
AA 6F C8 CD 21 1E F8 CE  to  D5 AA 41 24 CE BD 77 6F
and split to
seed = 2441AAD5 (beware of the endians !)
conf = CEBD776F

The reply :

conf + 0x00000000
CE BD 77 6F 00 00 00 00
Portal encrypt the reply :
CE BD 77 6F 00 00 00 00  to  25 65 BA E2 A6 5A 33 E9
And use it as payload

55: Always 0x55
09: Payload size
03: Message counter (same as query)
25 65 BA E2 A6 5A 33 E9: TEA crypted Payload
A3: Checksum

Seed is used for B3 message 
```

### b3 : challenge to authentify the portal TEA crypted
```
55: Always 0x55
0A: Payload size
B3: 0xb3 Command
04: Message counter
05 D8 98 E8 A7 9A B6 30: TEA crypted Payload
9A: Checksum

Portal decrypt the message and split to 
junk + conf and use is prng to compose reply : rand + conf as payload
crypt the payload :

55: Always 0x55
09: Payload size
04: Message counter
71 70 83 33 DF E3 02 C5: TEA crypted Payload
82: Checksum
```

### 0x56 incoming message from portal : This is an event related to NFC tag
```
56: Always 0x56
0b: Payload size
03: pad on the portal (center:01, left:02, right:03)
00: ??
00: index (this is the tag idx in the tags list maintained by the portal)
00: event type (placed:00, removed:01)
04 2a b5 d2 a2 40 80: tag UID
7b: Checksum
```

### d2 : NFC read ntag page (for vehicle)
```
(unclear : PS3 ask for page 0x26 but my code ask for 0x24 to get the data)

55: Always 0x55
04: Payload size
d2: 0xd2 Command
08: Message counter
00: index as specified in 0x56 event message (we tell the portal which tag to use)
26: NTAG page number
59: Checksum

Portal reply:
55: Always 0x55
12: Payload size
08: Message counter
00: index (this is the tag idx in the tags list maintained by the portal)
00 00 00 00: requested data from page
00 00 00 00: page + 1
60 0c 3f bd: page + 2
04 00 00 1e: page + 3
f9 : Checksum

My code request page 0x24 (byte 144 & 145) : getting "f0 03" for the Batmobil
f0 03 -> endianess change -> 0x03f0 -> 1008 -> tag reference for the Batmobil (cf .h file)

```
### d3 : NFC write
(not tested)

### d4 : NFC get tag model (for characters) TEA crypted
```
This seems to work only for characters tags not vehicles/gadgets.

Request :
55: Always 0x55
0a: Payload size
d4: 0xd4 Command
09: Message counter
15 dd a0 5b 8e ab 5c 68: TEA crypted Payload
26: Checksum

The PS3 compose the payload as :
00: index as specified in 0x56 event message 
4C 26 93: ???
62 FF 06 84: junk ?

Then encrypt the payload :
00 4C 26 93 62 FF 06 84  >  15 DD A0 5B 8E AB 5C 68
and send it to the portal.

The portal reply:
55: Always 0x55
0a: Payload size
09: Message counter
00: index (this is the tag idx in the tags list maintained by the portal, same as we request)
a8 54 84 03 75 3f bf 24: TEA crypted Payload
82: Checksum

PS3 decrypt the payload:
A8 54 84 03 75 3F BF 24  > 13 00 00 00 62 FF 06 84
and split it as
13 00 00 00 -> endianess change -> 0x00000013 -> 19 -> tag id for Gollum (cf .h file)

Note that the "62 FF 06 84" uncrypted from the request is added to the reply before encryption.
```

