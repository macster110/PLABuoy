#!/bin/bash
#?DEVICE="rPi";PATH="/usr/lib/cgi-bin/"

HOST='192.168.102.2'
USER=''
PASSWD=''

ftp -n -i $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
cd /u/
mls . /tmp/list.txt
quit
END_SCRIPT

echo -e "Content-type: text/html\n\n"
echo "<h1>Results of FTP test:</h1>"
echo "<p>`wc -l < /tmp/list.txt` files in \"/u/\" directory on FTP server.</p>"
echo "<h2>Output of FTP command \"mls\":</h2>"
echo "<p>$(sed 's/$/<br>/' /tmp/list.txt)</p>"
rm /tmp/list.txt

exit 0