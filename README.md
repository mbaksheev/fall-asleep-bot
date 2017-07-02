# fall-asleep-bot
Simple bot to suspend PC.

If you are to lazy to leave your bed to go to keyboard to suspend PC, this bot helps you to do it from you bed via you mobile device.

## How it works
1. Register new bot in Telegram to get BotToken
2. Run fall-asleep-bot on you PC with bot token from the first step
3. Send `/sleep` command to suspend you PC

**Currently fall-asleep-bot works only on Linux OS with systemd. This bot does `sudo systemctl suspend` call.**

## How to register new bot in Telegram
To register new Telegram Bot you need talk with special bot - BotFather and follows his simple instructions. You can find details in [Telegram Bot Documentation](https://core.telegram.org/bots#3-how-do-i-create-a-bot).
This bot will give you Authorization Token, this token will be needed to run fall-asleep-bot.

## How to build the fall-asleep-bot
To build the bot you will need Java and Maven
1. Clone the repository
2. Perform `mvn clean compile assembly:single` - it will build single jar file which is included all dependencies.

## How to install the all-asleep-bot
### Linux OS
#### Run as java application
There are many ways to install the bot, the simplest way is just execute jar file with the following command:

```
java -jar target/fall-asleep-bot-1.0-SNAPSHOT-jar-with-dependencies.jar <telegram_bot_name> <authorization_token> <secret_phrase>
```
where:
* `telegram_bot_name` - name of your bot, which you registered with BotFather. It is required parameter.
* `authorization_token` - authorization token, which BotFather gave you. It is required parameter.
* `secret phrase` - this phrase should known only users who will be allowed to suspend PC. It is required parameter.

But if you run the bot as simple java application the bot will be not automatically at boot.
Another way is run the bot as systemd service.

#### Run as systemd service

1. Create .sh script, e.g. `start.sh` with run command, it will be useful to specify bot settings here
```
#!/bin/sh
java -jar /path/to/fall-asleep-bot.jar <telegram_bot_name> <authorization_token> <secret_phrase>
```
2. Create fall-asleep-bot.service file in /etc/systemd/system with the following content:
```
[Unit]
Description=Fall Asleep bot service

[Service]
User=your_user
ExecStart=/path/to/start.sh

[Install]
WantedBy=multi-user.target
```
3. Add ability for your_user perform `sudo systemctl suspend` command without password - add the following to the end of sudoers with visudo
```
you_user ALL=NOPASSWD: /bin/systemctl suspend
```
4. Enable start service automatically at boot
```
sudo systemctl enable fall-asleep-bot.service
```
## How to use the bot
Start dialog with your bot in telegram. It will send list of commands.
Also you can send `/help` to get list of commands.

To suspend computer you should be logged in. To login send `/on`, after that input secret phrase which has been specified as bot arguments and send it.
If secret phrase is successfully verified you will see success message and you will be able suspend computer.

To suspend computer send `/sleep` command and if all is ok you will see success message and your PC will be suspended in a few seconds.
To log out send `/stop` command.

You should not loggin each time when you want to suspend computer. It is needed only once, but if you computer was restarted or fall-asleep-bot was restarted, users database will be reset (due to it is just a hashmap) and you should login again.