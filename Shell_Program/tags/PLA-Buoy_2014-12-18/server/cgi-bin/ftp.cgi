#!/bin/bash
#?DEVICE="rPi";PATH="/usr/lib/cgi-bin/"

#Set default value for $dir in case not given in URL query string
dir="/u/"

#Get $dir variable from the URL query string
#(e.g. www.example.com?file=myfile.txt)
. URLcoder.sh
cgi_getvars BOTH ALL

#Get $HOST, $USER and $PASSWD variables
. ftpVariables.sh

TEMPFILE=$(mktemp)

echo -e "Content-type: text/html\n\n"
echo "<p>Attempting FTP connection with user: \"${USER}\" @ host: \"${HOST}\".</p>"
ftp -n -i $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
cd $dir
ls . $TEMPFILE
quit
END_SCRIPT

cat <<xxxxx_END_HTML_xxxxx
<h1 id="top">Results of FTP test:</h1>
<p>$(wc -l < $TEMPFILE) files in "$dir" directory on FTP server.</p>
<h2>Output of FTP command "ls":</h2><p>Click file name to transfer to Pi for downloading or analysis. (Takes up to 30 seconds).</p>
<p>Go to <a href="#bottom">bottom</a> of page.</p>
<p><table><tr><td>$(sed -e 's/  */<\/td><td>/g' -e "s|\([^>]*\)$|<a href=\"sendToPi.cgi?dir=$dir\&file=\1\">\1<\/a><\/td><\/tr><tr><td>|" -e "s|\(^d[^?]*\)sendToPi.cgi?dir=$dir&file=\([^\"]*\)|\1ftp.cgi?dir=${dir}\2/|" <$TEMPFILE)</td></tr></table></p>
<p id="bottom">Go to <a href="#top">top</a> of page.</p>
xxxxx_END_HTML_xxxxx

rm $TEMPFILE

exit 0