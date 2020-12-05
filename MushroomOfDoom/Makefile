# ADD JAR FILES TO THE FOLLOWING LINE, SEPARATED BY ':' (NO WHITESPACE)
JARS :=

# DO NOT EDIT BELOW HERE
JAVA	:= java
JAVAC	:= javac
JUNIT   := /home/codio/bin/junit-platform-console-standalone-1.6.1.jar

SUBMIT    := $(wildcard src/*.java src/*/*.java test/*.java test/*/*.java files/* files/*/* *.jar Makefile README.txt)
TESTS_SRC := $(wildcard test/*.java)
TESTS     := $(patsubst test/%.java, bin/%.class, $(TESTS_SRC))
DEPENDS   := $(wildcard src/*.java)

HWNAME := hw09
ts := $(shell /bin/date "+%Y-%m-%d-%H:%M:%S")

ZIPNAME := $(HWNAME)-submit($(ts)).zip

.PHONY: all game run clean zip checkstyle test

all:	

bin:
	mkdir bin

bin/Game.class : $(DEPENDS) bin
	$(JAVAC) -cp .:$(JARS) -d bin $(DEPENDS)

run : bin/Game.class
	$(JAVA) -cp .:./bin Game

test: $(TESTS_SRC) $(JUNIT) bin 
	$(JAVAC) -d bin -cp $(JUNIT):bin/ $(TESTS_SRC)
	$(JAVA) -jar $(JUNIT) --classpath bin --scan-class-path --disable-banner

checkstyle: ~/bin/checkstyle.sh ~/bin/cis120-checkstyle-config.xml ~/bin/checkstyle-8.12-all.jar
	cd ~/bin && ./checkstyle.sh

zip:	$(SUBMIT)
	zip -r '$(ZIPNAME)' $(SUBMIT)

clean:
	rm -f src/*.class bin/* test/*.class
	rm -rf *.zip
