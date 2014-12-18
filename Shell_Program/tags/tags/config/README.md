Configuring the Raspberry Pi WiFi Hotspot
=========================================

This must be done manually for the time being, but hopefully this will
eventually be done automatically via the *configurePi.sh* and *deconfigurePi.sh* scripts."

##1. Initial setup
  1. Install Rasperian - follow [guide](http://www.raspberrypi.org/help/noobs-setup/).
  2. Update Rasperian and included programs. - `sudo apt-get update`, `sudo apt-get upgrade`.
  3. Enable SSH (allows remote control of rPi over network via command line)
    - Boot into Command Line and type `raspi-config`
    - Go to “Advanced” and “Enable SSH”.
  4. *(Only if needed)* Enable VNC Remote Desktop Client (allows remote control via GUI,
        useful if you don’t have a separate monitor for the Pi.)
    - follow this [guide](http://www.raspberrypi.org/documentation/remote-access/vnc/).
  
##2. Install required packages
Type: `sudo apt-get install rfkill zd1211-firmware hostapd hostap-utils iw dnsmasq apache2 vsftpd ftp`
(You can copy and paste these commands into a Terminal window, but note that you must use Ctrl+Shift+C
and Ctrl+Shift+V to Copy/Paste within a Terminal window as Ctrl+C and Ctrl+V do other things.)

##3. Copy config files to Raspberry Pi
The required location for each file is given near the top of each file after a `#?`. E.g. "hostapd.conf":

>   #?PATH="/ect/hostapd/hostapd.conf"
>
>   interface=wlan0
>   driver=nl80211
>   ssid=Buoy
>   channel=1
>   wpa=2
>   wpa_passphrase=AlwaysChangeTheDefaultPassword
>   wpa_key_mgmt=WPA-PSK
>   wpa_pairwise=TKIP
>   rsn_pairwise=CCMP

If a file already exits there with the same name then rename the existing one "hostadp.conf_backup".

##4. Make the startup script executeable
The "pipoint" config file is really a script and therefore requires execute permission to run on startup.
  1. Open a terminal and change directory to the location of the file `cd /etc/init.d/`
  2. `sudo chmod +x pipoint` and `sudo update-rc.d pipoint start 99 2`

##5. Enable IPv4 Packet Forwarding (Network Bridge Mode)
This allows the Raspberry Pi's wireless and wired clients to communicate with each other.
  1. Type: “sudo nano /etc/sysctl.conf”
  2. Uncomment line `#net.ipv4.ip_forward=1` (i.e. delete the `#` at beginning of the line)
  3. Save and exit.