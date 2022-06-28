CC := gcc
CFLAGS := -Wall -c
SRCS := gpio.c
OBJS := $(SRCS:.c=.o)
TARGET := gpio

all: $(TARGET)
$(TARGET): ${OBJS}
    ${CC} gpio.c -o gpio
gpio.o: gpio.c
    ${CC} ${CFLAGS} gpio.c
.PHONY: clean
clean:
    rm -f $(OBJS)