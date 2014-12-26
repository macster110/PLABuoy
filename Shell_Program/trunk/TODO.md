TODO list:
==========

1. Server: write "updatePi.sh" script to automatically copy webpages and CGI scripts across to the Pi over WiFi.

2. Config: Write "configurePi.sh" script to automatically set the Pi up as a web server.
  - Problem: Configuration involves changing networks settings while connected to Pi over a network
  - Problem: Config files need to go to restricted directories (owned by root). Solution?: Copy into unrestricted (owned by USER) directory (e.g. $HOME or "/tmp"?) via FTP and then move to restricted directory using SSH.

3. Create template HTML+CSS files. Scripts should access templates and insert content rather than generating an entire page within the script.