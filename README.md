# TelnetPhone
I wrote a tool in java to control several phones, working as T4 auto script, used for reproducing bugs. And it is easy to use and modify, if you have any questions, feel free to ask me.

What you need to do is modify TestMain.java to add your phones and script, compile the code (including Apache’s net lib), and run.
here’s the link to download Apache’s lib:
commons-net-3.4-bin.zip

Note:
there’re 3 mode for telnet connection: 
MODE_SHELL: linux shell
MODE_DEBUG_SHELL: phone’s debug shell
MODE_DEBUG_SHELL_TEST: phone’s debug shell with test opening, then you can simulate key event

So you can specify the mode when you send command, the default mode is MODE_DEBUG_SHELL_TEST
