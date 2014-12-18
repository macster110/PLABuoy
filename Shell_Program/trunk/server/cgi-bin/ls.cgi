#!/bin/bash
#?DEVICE="rPi";PATH="/usr/lib/cgi-bin/"

TEMPFILE=$(mktemp)

echo -e "Content-type: text/html\n\n"
echo "<p>Listing files stored on Pi</p>"

cd /var/www/download/
ls -l >$TEMPFILE

cat <<xxxxx_END_HTML_xxxxx
<h2 id="top">Files stored on Pi ("var/www/download/"):</h2>
<p>$(wc -l < $TEMPFILE) files in "/download/" directory on Pi.</p>
<h2>Output of UNIX command "ls -l":</h2><p>Click file name to view file or right-click to save file. WAV files can also be analysed.</p>
<p>Go to <a href="#bottom">bottom</a> of page.</p>
<p><table><tr><td>$(sed -e 's/  */<\/td><td>/g' -e 's/\([^>]*\)$/<a href="..\/download\/\1">\1<\/a><\/td><\/tr><tr><td>/' -e 's/\([^>]*\).wav<\/a>/&<\/td><td><a href="analyseWAV.cgi?file=\1.wav">(analyse)<\/a>/' <$TEMPFILE)</td></tr></table></p>
<p id="bottom">Go to <a href="#top">top</a> of page.</p>
xxxxx_END_HTML_xxxxx

rm $TEMPFILE

exit 0