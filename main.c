#include <sys/ioctl.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h> 
#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>
#include <termio.h>
#include <sys/time.h>
#include <sys/errno.h>
#include <asm/types.h>

#include <linux/hiddev.h>

#include "vehicles_and_gadgets.h"
#include "characters.h"

#define ANSI_COLOR_RED     "\x1b[31m"
#define ANSI_COLOR_GREEN   "\x1b[32m"
#define ANSI_COLOR_YELLOW  "\x1b[33m"
#define ANSI_COLOR_BLUE    "\x1b[34m"
#define ANSI_COLOR_MAGENTA "\x1b[35m"
#define ANSI_COLOR_CYAN    "\x1b[36m"
#define ANSI_COLOR_RESET   "\x1b[0m"

// lego Toy Pad
#define VID 0x0e6f
#define PID 0x0241

#define BUFFER_SIZE 32

//#define DEBUG 1

#define PAN_ALL    0
#define PAN_CENTER 1
#define PAN_LEFT   2
#define PAN_RIGHT  3

#define CMD_WAKE    0xb0
#define CMD_SEED    0xb1
#define CMD_CHAL    0xb3
#define CMD_COL     0xc0
#define CMD_FADE    0xc2
#define CMD_FADRD   0xc4
#define CMD_FADAL   0xc6
#define CMD_FLSAL   0xc7
#define CMD_COLAL   0xc8
#define CMD_TGLST   0xd0
#define CMD_READ    0xd2
#define CMD_WRITE   0xd3
#define CMD_MODEL   0xd4
#define CMD_E1      0xe1
#define CMD_E5      0xe5
#define CMD_LEDSQ   0xff

// sent message counter
unsigned char messagenumber=1;

unsigned char key[] = { 0x55, 0xFE, 0xF6, 0xB0, 0x62, 0xBF, 0x0B, 0x41, 0xC9, 0xB3, 0x7C, 0xB4, 0x97, 0x3E, 0x29, 0x7B };   // 4*32b Little Endian (x86)

// Hue/Saturation/Value to RGB color transform
void HSVtoRGB(int  *r, int *g,int *b, int h, int s, int v) {
  int c;
  long l, m, n;

  // zero saturation, no color
  if(s == 0) {
    *r = *g = *b = v;
    return;
  }

  // chroma
  c = ((h%60)*255)/60;
  h /= 60;

  // intermediate
  l = (v*(256-s))/256;
  m = (v*(256-(s*c)/256))/256;
  n = (v*(256-(s*(256-c))/256))/256;

  // choose dominant
  switch(h) {
    case 0:
      *r = v; *g = n; *b = l; break;
    case 1:
      *r = m; *g = v; *b = l; break;
    case 2:
      *r = l; *g = v; *b = n; break;
    case 3:
      *r = l; *g = m; *b = v; break;
    case 4:
      *r = n; *g = l; *b = v; break;
    default:
      *r = v; *g = l; *b = m; break;
  }
}

// TEA encryption routine (from wikipedia)
void teaencrypt (uint32_t *v, uint32_t *k) {
        uint32_t v0=v[0], v1=v[1], sum=0, i;           /* set up */
        uint32_t delta=0x9e3779b9;                     /* a key schedule constant */
        uint32_t k0=k[0], k1=k[1], k2=k[2], k3=k[3];   /* cache key */

        for (i=0; i < 32; i++) {                       /* basic cycle start */
                sum += delta;
                v0 += ((v1<<4) + k0) ^ (v1 + sum) ^ ((v1>>5) + k1);
                v1 += ((v0<<4) + k2) ^ (v0 + sum) ^ ((v0>>5) + k3);
        }                                              /* end cycle */

        v[0]=v0; v[1]=v1;
}

// TEA decryption routine (from wikipedia)
void teadecrypt (uint32_t *v, uint32_t *k) {
        uint32_t v0=v[0], v1=v[1], sum=0xC6EF3720, i;  /* set up */
        uint32_t delta=0x9e3779b9;                     /* a key schedule constant */
        uint32_t k0=k[0], k1=k[1], k2=k[2], k3=k[3];   /* cache key */

        for (i=0; i<32; i++) {                         /* basic cycle start */
                v1 -= ((v0<<4) + k2) ^ (v0 + sum) ^ ((v0>>5) + k3);
                v0 -= ((v1<<4) + k0) ^ (v1 + sum) ^ ((v1>>5) + k1);
                sum -= delta;
        }                                              /* end cycle */

        v[0]=v0; v[1]=v1;
}

// Read a unsigned 32b int stored as 4char Big Endian
uint32_t readUInt32BE(unsigned char *buf, int pos) {
        uint32_t b0,b1,b2,b3;
        uint32_t res;

        b0 = buf[pos] << 24u;
        b1 = buf[pos+1] << 16u;
        b2 = buf[pos+2] << 8u;;
        b3 = buf[pos+3];

        res = b0 | b1 | b2 | b3;
        return(res);
}

// Read a unsigned 32b int stored as 4char Little Endian
uint32_t readUInt32LE(unsigned char *buf, int pos) {
        uint32_t b0,b1,b2,b3;
        uint32_t res;

        b0 = buf[pos];
        b1 = buf[pos+1] << 8u;
        b2 = buf[pos+2] << 16u;
        b3 = buf[pos+3] << 24u;

        res = b0 | b1 | b2 | b3;
        return(res);
}

// Write a unsigned 32b int stored as 4char Big Endian
void writeUInt32BE(uint32_t val, unsigned char *buf, int pos) {
        buf[pos] = (val & 0xff000000) >> 24u;
        buf[pos+1] = (val & 0x00ff0000) >> 16u;
        buf[pos+2] = (val & 0x0000ff00) >> 8u;
        buf[pos+3]= val & 0x000000ff;
}

// Write a unsigned 32b int stored as 4char Little Endian
void writeUInt32LE(uint32_t val, unsigned char *buf, int pos) {
        buf[pos] = val & 0x000000ff;
        buf[pos+1] = (val & 0x0000ff00) >> 8u;
        buf[pos+2] = (val & 0x00ff0000) >> 16u;
        buf[pos+3] = (val & 0xff000000) >> 24u;
}

// tea decrypt 8 bytes (2*uint32_t) payload
void decryptPayload(unsigned char *buf) {
	int i;
        uint32_t data[2];
        uint32_t keya[4];
#ifdef DEBUG
	printf("decrypt: ");
	for(i=0; i<8; i++) printf("%02X ", buf[i]);
	printf("> ");
#endif
        keya[0] = readUInt32LE(key,0);
        keya[1] = readUInt32LE(key,4);
        keya[2] = readUInt32LE(key,8);
        keya[3] = readUInt32LE(key,12);

        data[0] = readUInt32LE(buf,0);
        data[1] = readUInt32LE(buf,4);

        teadecrypt(data, keya);

        writeUInt32LE(data[0], buf, 0);
        writeUInt32LE(data[1], buf, 4);
#ifdef DEBUG
	for(i=0; i<8; i++) printf("%02X ", buf[i]);
	printf("\n");
#endif
}

// tea encrypt 8 bytes (2*uint32_t) payload
void encryptPayload(unsigned char *buf) {
	int i;
        uint32_t data[2];
        uint32_t keya[4];
#ifdef DEBUG
	printf("encrypt: ");
	for(i=0; i<8; i++) printf("%02X ", buf[i]);
	printf("> ");
#endif
        keya[0] = readUInt32LE(key,0);
        keya[1] = readUInt32LE(key,4);
        keya[2] = readUInt32LE(key,8);
        keya[3] = readUInt32LE(key,12);

        data[0] = readUInt32LE(buf,0);
        data[1] = readUInt32LE(buf,4);

        teaencrypt(data, keya);

        writeUInt32LE(data[0], buf, 0);
        writeUInt32LE(data[1], buf, 4);
#ifdef DEBUG
	for(i=0; i<8; i++) printf("%02X ", buf[i]);
	printf("\n");
#endif
}

void send_message(int fd, int id, unsigned char *buf, int n) {
  struct hiddev_usage_ref_multi uref;
  struct hiddev_report_info rinfo;
  int i;
  int checksum=0;

  // fill usages struct to send
  uref.uref.report_type = HID_REPORT_TYPE_OUTPUT;
  uref.uref.report_id = id;
  uref.uref.field_index = 0;
  uref.uref.usage_index = 0;
  uref.num_values = n;

  // change message number
  buf[3]=messagenumber;
  // compute checksum
  // using buf[1] as payload size to match payload data
  for(i=0; i<2+buf[1]; i++) {
	  checksum+=buf[i];
	  checksum = checksum & 0xff;
  }
  // insert updated checksum
  // using buf[1] as payload size to find the right spot
  buf[2+buf[1]]=checksum;

  for (i = 0; i < n; i++)
    uref.values[i] = buf[i];

  // Sets the value of a usage in an output report
  if (ioctl(fd, HIDIOCSUSAGES, &uref) < 0) {
    fprintf(stderr,"HIDIOCSUSAGE Error : %s (%d)\n",
        strerror(errno),errno);
    close(fd);
    exit(EXIT_FAILURE);
  }

  // build the report itself
  rinfo.report_type = HID_REPORT_TYPE_OUTPUT;
  rinfo.report_id = id;
  rinfo.num_fields = 1;

  // Instructs the kernel to send a report to the device
  if (ioctl(fd, HIDIOCSREPORT, &rinfo) < 0) {
    fprintf(stderr,"HIDIOCSREPORT Error : %s (%d)\n",
        strerror(errno),errno);
    close(fd);
    exit(EXIT_FAILURE);
  }

#ifdef DEBUG
  printf("> ");
  for(i=0; i<32; i++)
	  printf("%02X ", buf[i]);
  printf("     message: %02X\n", messagenumber);
#endif

  messagenumber++;
}

void setcolor(int fd, int panel, int speed, int r, int g, int b) {
  // int i;
  // int checksum=0;
  unsigned char msg[] = { 0x55, 0x08, 0xc2, 0x00, 0x00, 0x00, 0x00, 0x00, 
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

  msg[3] = 0; // messagenumber : send_message() take care of messagenumber and checksum
  msg[4] = panel & 0xff;  // panel num
  msg[5] = speed & 0xff;  // Transition time / Pulse time, 0x00 is immediate
  msg[6] = 0x01;          // Pulse count, 0x00 is forever odd leaves on new colour, even leaves on old colour. 0xc8 and above is forever
  msg[7] = r & 0xff;
  msg[8] = g & 0xff;
  msg[9] = b & 0xff;
  
  send_message(fd, 0x00, msg, 32);
}

int wait_for_input(int fd, int timeout) {
	fd_set fds;
	struct timeval tv, *tvp;

	// compute time
	tvp = 0;
	if (timeout >= 0) {
		tv.tv_sec = timeout / 1000;
		tv.tv_usec = timeout % 1000 * 1000;
		tvp = &tv;
	}
	// clear set
	FD_ZERO(&fds);
	// Add fd to the set
	FD_SET(fd, &fds);

	// wait for fd to become ready to read
	return select(fd + 1, &fds, NULL, NULL, tvp);
}

void wait_report(int fd, int timeout) {
	struct hiddev_usage_ref uref;

	if (wait_for_input(fd, timeout) > 0)
		while (read(fd, &uref, sizeof(uref)) > 0)
			;
}

void query_report(int fd, int id, unsigned char *buf, int n) {
	struct hiddev_usage_ref_multi uref;
	struct hiddev_report_info rinfo;
	int i;

	rinfo.report_type = HID_REPORT_TYPE_INPUT;
	rinfo.report_id = id;
	rinfo.num_fields = 1;
	if (ioctl(fd, HIDIOCGREPORTINFO, &rinfo) < 0) {
		fprintf(stderr,"HIDIOCGREPORTINFO Error : %s (%d)\n",
				strerror(errno),errno);
		close(fd);
		exit(EXIT_FAILURE);
	}

//	wait_report(fd, 3000);
	wait_report(fd, -1); // wait forever

	uref.uref.report_type = HID_REPORT_TYPE_INPUT;
	uref.uref.report_id = id;
	uref.uref.field_index = 0;
	uref.uref.usage_index = 0;
	uref.num_values = n;
	if (ioctl(fd, HIDIOCGUSAGES, &uref) < 0) {
		fprintf(stderr,"HIDIOCGUSAGES Error : %s (%d)\n",
				strerror(errno),errno);
		close(fd);
		exit(EXIT_FAILURE);
	}

	memset(buf, 0, sizeof(unsigned char)*BUFFER_SIZE);

	// get message from report and fill buffer
	for (i = 0; i < n; ++i)
		buf[i] = uref.values[i];

#ifdef DEBUG
	// display incoming message
	printf("< ");
	for(i=0; i<32; i++) 
		printf("%02X ", buf[i]);
	printf("\n");
#endif
}

int main(int argc, char **argv) {
  // init msg  --  "(c) LEGO 2014"
  unsigned char initmsg[] = { 0x55, 0x0F, 0xB0, 0x01, 0x28, 0x63, 0x29, 0x20, 
	  		      0x4C, 0x45, 0x47, 0x4F, 0x20, 0x32, 0x30, 0x31, 
			      0x34, 0xF7, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

  // post init msg B1
  unsigned char b1msg[] = { 0x55, 0x0a, 0xb1, 0x03, 0xaa, 0x6f, 0xc8, 0xcd,
			    0x21, 0x1e, 0xf8, 0xce, 0xc6, 0x00, 0x00, 0x00,
			    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

  // post init msg B2
  unsigned char b3msg[] = { 0x55, 0x0a, 0xb3, 0x04, 0x29, 0x43, 0x7a, 0xe1,
	  		    0x69, 0xb0, 0x30, 0x8b, 0xb1, 0x00, 0x00, 0x00,
			    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

  unsigned char query_tinfo[] = { 0x55, 0x04, 0xd2, 0x32, 0x03, 0x26, 0x86, 0x00, 
				  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
				  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
				  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

  int i;
  //int r, g, b;

  // name of the device
  struct hiddev_string_descriptor str_desc;
  unsigned char devicestring[256];

  // buffer for incomming messages
  unsigned char getbuf[256];

  struct hiddev_devinfo dinfo;
  int fd;

  int checksum;

  // to store query message number for D2 and D4 messages
  int D2query_number=-1;
  int D4query_number=-1;

  // size of the vehicle/gadget tag list
  int tagsNamesLen = sizeof(legoVehicleStr)/sizeof(legoVehicleStr[0]);
  // tag reference in page 24 of the ntag213
  int tagRef = 0;
  // endian corrected reference - 1000 to get index in char array
  int tagRefIdx = 0;

  // size of the characters tag list
  int tagsCNamesLen = sizeof(legoCharacterStr)/sizeof(legoCharacterStr[0]);
  // tag reference returned after D4 query
  int tagCRefIdx = 0;

  // open hiddev node
  if ((fd=open("/dev/usb/hiddev0", O_RDWR)) < 0) { 
    fprintf(stderr,"Open Error : %s (%d)\n",
        strerror(errno),errno);
    exit(EXIT_FAILURE);
  }

  // get info
  if (ioctl(fd, HIDIOCGDEVINFO, &dinfo) < 0) {
    fprintf(stderr,"Get device info Error : %s (%d)\n",
        strerror(errno),errno);
    close(fd);
    exit(EXIT_FAILURE);
  }

  // check VID/PID
  if (dinfo.vendor != (short)VID || dinfo.product != (short)PID) {
    fprintf(stderr,"Sorry, not my device\n");
    close(fd);
    exit(EXIT_FAILURE);
  }
  printf("Got my device\n");

  // get and print device string
  memset(devicestring, 0, sizeof(devicestring));
  if (ioctl(fd, HIDIOCGNAME(256), devicestring) < 0) {
    fprintf(stderr,"Get device info Error : %s (%d)\n",
        strerror(errno),errno);
    close(fd);
    exit(EXIT_FAILURE);
  }
  printf("Device string : %s\n", devicestring);
  str_desc.index=1;
  while (ioctl(fd, HIDIOCGSTRING, &str_desc) >= 0) {
    printf("string%u : %s\n", str_desc.index, str_desc.value);
    str_desc.index++;
  }

  // set read() to return struct hiddev_usage_ref, instead of struct hiddev_event
  int flags = HIDDEV_FLAG_UREF | HIDDEV_FLAG_REPORT;
  if (fcntl(fd, F_SETFL, O_RDWR | O_NONBLOCK) < 0) {
	  fprintf(stderr,"O_NONBLOCK Error : %s (%d)\n",
			  strerror(errno),errno);
	  close(fd);
	  exit(EXIT_FAILURE);
  }
  if (ioctl(fd, HIDIOCSFLAG, &flags) < 0) {
	  fprintf(stderr,"HIDIOCSFLAG Error : %s (%d)\n",
			  strerror(errno),errno);
	  close(fd);
	  exit(EXIT_FAILURE);
  }
 
  /*
   * INIT
   */
  printf("report initmsg\n");
  send_message(fd, 0x00, initmsg, 32);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);

  printf("report color\n");
  setcolor(fd, PAN_ALL, 0,  64, 64, 32);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);

  // useless, this is the console authenticating the portal
  // seed (TEA stuff)
  // TODO : replace with real TEA stuff
  printf("report b1msg\n");
  send_message(fd, 0x00, b1msg, 32);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  // challenge (TEA stuff)
  // TODO : replace with real TEA stuff
  printf("report b3msg\n");
  send_message(fd, 0x00, b3msg, 32);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);

  /*
  setcolor(fd, PAN_CENTER, 0,  255,   60,   0); usleep(500000);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  setcolor(fd, PAN_LEFT, 0,  255,  160,   0); usleep(500000);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  setcolor(fd, PAN_RIGHT, 0,   0,   255,   0); usleep(500000);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  setcolor(fd, PAN_ALL, 0,  255,  255, 180); usleep(500000);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  setcolor(fd, PAN_ALL, 0,  255, 0,   0); usleep(500000);
  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);
  */
  
  usleep(500000);

  /*
  for(j=0;j<5;j++) {
	  for(i=0;i<360;i++) {
		  HSVtoRGB( &r, &g, &b, i, 255, 255);
		  setcolor(fd, PAN_ALL, 0, r, g, b);
		  usleep(10000);
	  }
  }
  */

  unsigned char cryptopayload[8];

  while(1) {
	  checksum=0;
	  query_report(fd, HID_REPORT_ID_FIRST, getbuf, 32);

	  // 0x55 magic : "normal" messages
	  if(getbuf[0]==0x55) {
		  /*
		  printf("Payload Size : (%u)         ", getbuf[1]);
		  for(i=0; i<2+getbuf[1]; i++) {
			  checksum+=getbuf[i];
			  checksum = checksum & 0xff;
		  }
		  printf("checksum (%02X) ", checksum);
		  printf(checksum==getbuf[2+getbuf[1]] ? "ok" : "NOT OK");
		  printf("\n");
		  */

		  // last sent message set this ? Are we waiting for answer ?
		  if(D2query_number >=0 ) {
			  if(getbuf[2] == D2query_number) {
				  // This is the answer we are waiting fot
				  printf("Reply to tag read cmd (message %02X): ", D2query_number);
				  // reset query number, we are processing it right now
				  D2query_number = -1;
				  printf("%02X%02X   ", getbuf[4], getbuf[5]);
				  tagRef = getbuf[5] << 8 | getbuf[4];
				  if(tagRef>=1000) {
					  // no tags seems to have ID/Ref < 1000
					  printf("%04X = %u   ", tagRef, tagRef);
					  tagRefIdx = tagRef-1000;
					  // tag reference-1000 in range ?
					  if(tagRefIdx < tagsNamesLen) {
						  printf("This is : " ANSI_COLOR_GREEN "%s" ANSI_COLOR_RESET, legoVehicleStr[tagRefIdx]);
						  switch(tagRefIdx % 3) {
							  // We have built,rebuilt1,rebuilt2,built,rebuilt1,rebuilt2... in the list
							  case 0:	printf("  (basic build)\n");
									break;
							  case 1:	printf("  (rebuild 1 of: %s)\n", legoVehicleStr[tagRefIdx-1]);
									break;
							  case 2:	printf("  (rebuild 2 of: %s)\n", legoVehicleStr[tagRefIdx-2]);
						  }
					  } else {
						  printf("VEHICLE NOT IN MY LIST !\n");
					  }
				  } else {
					  // ID < 1000, wrong reading or wrong page read
					  printf("NOT A VALID TAG ID/REF (<1000)\n");
				  }
			  }
		  }
		  if(D4query_number >=0 ) {
			  if(getbuf[2] == D4query_number) {
				  for(i=0; i<8; i++) {
					  cryptopayload[i]=getbuf[i+4];
				  }
				  decryptPayload(cryptopayload);
				  printf("Reply to D4 cmd (message %02X)  ", D4query_number);
				  // reset query number, we are processing it right now
				  D4query_number = -1;
				  tagCRefIdx = readUInt32LE(cryptopayload,0);
				  printf("character ID = 0x%08X = %u       ", readUInt32LE(cryptopayload,0), tagCRefIdx);
				  if(tagCRefIdx<tagsCNamesLen) {
					  printf("This is : " ANSI_COLOR_GREEN "%s" ANSI_COLOR_RESET "\n", legoCharacterStr[tagCRefIdx]);
				  } else {
					  printf("CHARACTER NOT IN MY LIST !\n");
				  }
			  }
		  }
	  }

	  // 0x56 magic : tag event
	  if(getbuf[0]==0x56) {
		  printf("Tag UID: ");
		  for(i=6; i<13; i++) {
			  printf("%02X", getbuf[i]);
		  }
		  printf("    action:");
		  printf(getbuf[5]==0x00 ? "PLACED" : "REMOVED");
		  printf("    PAD : %u", getbuf[2]);
		  if(getbuf[2]==0x01) printf("(center)");
		  if(getbuf[2]==0x02) printf("(left)");
		  if(getbuf[2]==0x03) printf("(right)");
		  checksum = 0;
		  for(i=0; i<2+getbuf[1]; i++) {
			  checksum+=getbuf[i];
			  checksum = checksum & 0xff;
		  }
		  printf("     checksum (%02X) ", checksum);
		  printf(checksum==getbuf[2+getbuf[1]] ? "ok" : "NOT OK");
		  printf("\n");

		  // 0x00 -> tag placed
		  if(getbuf[5]==0x00) {
			  // Tag placed event : send D2 command to read page
			  setcolor(fd, getbuf[2], 0,  255, 0, 0);
			  memset(query_tinfo, 0, sizeof(unsigned char)*BUFFER_SIZE);
			  query_tinfo[0] = 0x55;  // magic
			  query_tinfo[1] = 0x04;  // payload size
			  query_tinfo[2] = 0xd2;  // command
			  // query_tinfo[3] = messagenumber; // useless send_message() take care of this
			  query_tinfo[4] = getbuf[4] ; // index in portal list
			  query_tinfo[5] = 0x24;  // tag page 0x26 / page 38, 39, 40, 41
			  D2query_number = messagenumber;
			  send_message(fd, 0x00, query_tinfo, 32);

			  // send D4 command to get model
			  memset(query_tinfo, 0, sizeof(unsigned char)*BUFFER_SIZE);
			  query_tinfo[0] = 0x55;
			  query_tinfo[1] = 0x0a;
			  query_tinfo[2] = 0xd4;
			  // replay data
			  // 15 DD A0 5B 8E AB 5C 68 > 00 4C 26 93 62 FF 06 84 (decrypted)
			  cryptopayload[0] = getbuf[4] ; // index in portal list ?
			  cryptopayload[1] = 0x4c;
			  cryptopayload[2] = 0x26;
			  cryptopayload[3] = 0x93;
			  cryptopayload[4] = 0x62;
			  cryptopayload[5] = 0xff;
			  cryptopayload[6] = 0x06;
			  cryptopayload[7] = 0x84;
			  encryptPayload(cryptopayload);
			  for(i=0; i<8; i++) {
				  query_tinfo[i+4]=cryptopayload[i];
			  }
			  D4query_number = messagenumber;
			  send_message(fd, 0x00, query_tinfo, 32);
		  } else if(getbuf[5]==0x01) {
			  setcolor(fd, PAN_ALL, 0,  64, 64, 32);
		  }
	  }
  }

  printf("close\n");
  close(fd);

  return(EXIT_SUCCESS);
}

