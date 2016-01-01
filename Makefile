TARGET  := main
WARN    := -Wall 
CFLAGS  := -O2 ${WARN} #-fdiagnostics-color=always
LDFLAGS := 
CC      := gcc


all: ${TARGET}

${TARGET}.o: ${TARGET}.c
${TARGET}: ${TARGET}.o

clean:
	rm -rf ${TARGET}.o ${TARGET}
