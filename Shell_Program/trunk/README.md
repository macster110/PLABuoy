Directory Structure
===================

Directory   | Contents
------------|----------------------------
**config/** | Instructions and configuration files to turn the Raspberry Pi into a WiFi hotspot and web server
**server/** | Scripts to be run on the Raspberry Pi web server to generate HTML files from cRio data

## Running the code

Follow the instruction in the *config/* directory to set up the WiFi Hotspot and web server, then follow the instructions in the *server/* directory to copy across the webpages and web scripts.

## Editing the code

You can edit the code using any text editor and SVN client, but it is recommended that you use the [Eclipse IDE](http://www.eclipse.org/downloads/) (Choose the basic "Eclipse IDE for Java Developers" package).

###1. Setting up Eclipse plugins

If using Eclipse you will need to install the following plugins from within Eclipse via "Help --> Eclipse Marketplace" (use the "Install More" option to install them all in one go.):

Plugin               | Purpose
---------------------|---------------------------------------
Subversive           | **(Required)** SVN Client
HTML Editor (WTP)    | (Recommended) For editing webpages (HTML+CSS+Javascript)
Markdown Text Editor | *(Optional)* For editing README.md files
GFM Viewer*          | *(Optional)* GitHub Flavoured Markdown viewer plugin (for displaying README.md files)

**If using GFM Viewer please go to: "Window-->Preferences-->GFM Viewer" and tick "Use temp dir" to prevent the code directories being filled up with invisible HTML files.*

###2. ShellEd Shell Script Editor plugin for Eclipse

It is also highly recommended that you also install ShellEd shell script editor for writing Bash scripts. Unfortunately this cannot be done via the Eclipse Marketplace. Instead:

1. Go to "Help --> Install New Software..."
2. Select "Work with --> All Available Sites". After a short wait a list of software should appear under "Name".
3. If nothing appears you may have this [bug](http://stackoverflow.com/questions/1965285). Exit Eclipse and delete your "dialog_settings.xml" file (see link).
4. Once you get the list working, in the filter text box type "ShellEd". It should appear under "Enide Tools Collection".
6. Install "ShellEd" and restart Eclipse.
7. Make ShellEd the default editor for CGI scripts: "Window-->Preferences-->General-->Editors-->File Associations". Create an entry for "*.cgi" and associate it with "Shell Script Editor".
7. If using a Linux machine you can also run the Shell Scripts within Eclipse. You must select the "Bash" interpreter.
(Go to: "Window-->Preferences-->Shell Script-->Interpreters-->Search" and select "Bash".)

###3. Adding the code to Eclipse

Once the necessary plugins are installed (and you have restarted Eclipse) you can add the code to your workspace in Eclipse by going to "File-->Import-->SVN-->Project from SVN-->Create a new repository location" and using the [Project URL (HTTP)](http://svn.code.sf.net/p/plabuoy/svn-code/Shell_Program). The latest version of the project is always kept in the "trunk" directory. Periodically the contents of "trunk" are copied across to "tags" to create an archive. "branches" are not used for this project (not enough collaborators to see a benefit).
