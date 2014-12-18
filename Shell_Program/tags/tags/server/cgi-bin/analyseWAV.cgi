#!/bin/bash
#?DEVICE="rPi";PATH="/usr/lib/cgi-bin/"

source URLcoder.sh
cgi_getvars BOTH ALL

#pass in file variable from URL query string
file=$file

echo -e "Content-type: text/html\n\n"
echo "<h2>Basic info:</h2>"

cd /var/www/download/
rm "spectrogram.png"

echo "<p>$(soxi ${file} | sed 's/$/<br>/')</p>"
echo "<h2>Channel info:</h2>"
echo "<table><tr><td>$(sox ${file} -n trim 0 1 stats 2>&1 | sed -e 's/  *\([-OC0-9]\)/<\/td><td align="center">\1/g' -e 's/$/<\/td><\/tr><tr><td>/')</td></tr></table>"
sox ${file} -n trim 0 1 spectrogram
echo "<h2>Spectrogram:</h2>"
echo '<img src="../download/spectrogram.png"></img>'

exit 0