Sending the CGI-scripts to the Raspberry Pi
===========================================

This must be done manually for the time being, but hopefully this will eventually be done automatically via the *updatePi.sh* and *cleanupPi.sh* scripts.

Make sure you have fully configured the Pi and got the WiFi Hotspot and webserver running according to the instructions in the *config/* directory. Connecting to the Pi over WiFi and navigating the http://buoy.wifi should take you to a page displaying the message "It works!".

##1. Copy the server files across to the Pi.
The files in the *www/* directory need to be put in */var/www/* and the files in *cgi-bin/* go in */usr/lib/cgi-bin/*

The files in *cgi-bin/* are CGI Scripts ([Common Gateway Interface](http://en.wikipedia.org/wiki/Common_Gateway_Interface)) that are used to create dynamic webpages (i.e. the content can change each time a user visits the page). Unlike HTML files, when the user navigates to one of these files (e.g. by going to http://buoy.wifi/cgi-bin/ftp.cgi) the file is *not* displayed in the browser. Instead the file is a program that is run on the Pi and the output of the program is sent to the user's browser and displayed like an ordinary webpage.

##2. Give the CGI Scripts permission to run.
Like all programs the CGI Scripts require execute permission. In the directory */usr/lib/cgi-bin/* type `sudo chmod +x *.cgi *.sh`.

##3. Testing.
Restart the Pi and connect via the "PLA-Buoy" WiFi network. Open a web browser at http://buoy.wifi. The links should work but you might get a few error messages or empty pages since the Compact Rio is not currently connected.

##3. Connecting the Compact Rio.
Connect the Compact Rio to the Pi via Ethernet and then restart both the Pi and the cRio. Try connecting via WiFi and accessing some files. The CGI Scripts use FTP to copy files from the cRio to the Pi. The Pi then performs calculations on the files and displays a spectogram. You can browse a list of files on the cRio or those that have been transferred to the rPi.