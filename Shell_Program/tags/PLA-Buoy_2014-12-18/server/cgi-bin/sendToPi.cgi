#!/bin/bash
#?DEVICE="rPi";PATH="/usr/lib/cgi-bin/"

#Get $file variable from the URL query string
#(e.g. www.example.com?file=myfile.txt)
. URLcoder.sh
cgi_getvars BOTH ALL

#Get $HOST, $USER and $PASSWD variables
. ftpVariables.sh

echo -e "Content-type: text/html\n\n"
echo "Attempting transfer of file \"${file}\" from dir \"${dir}\""
echo "from user: \"${USER}\" @ host: \"${HOST}\""

cd /var/www/download/
ftp -n -i $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
bin
cd $dir
get $file
quit
END_SCRIPT

echo -n "<h2>Received file: <a href=\"../download/$file\">$file</a>." \
| sed "s/.wav<\/a>./& Click to <a href=\"analyseWAV.cgi?file=$file\">analyse<\/a> (takes up to 30 seconds)./"
echo "</h2>"

exit 0