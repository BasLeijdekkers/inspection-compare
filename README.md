# Inspection Compare Plugin
Plugin for Intellij IDEA, PyCharm, RubyMine, PhpStorm... Compare the inspection results you have exported to a directoryes in XML. 
This plugin adds a new "Filter/Diff Inspection Results..." button to the Analyze menu.

General usage instructions:

On the main menu, choose Analyze | Filter/Diff Inspection Results...(or Code | Filter/Diff Inspection Results... in some IDEs)
In the Filter/Diff dialog select the Diff tab if you want to compare the inspection results or select the Filter tab if you want to filter the results by substring.

In the Diff tab, specify baseline and updated directores that contains the results in XML format. Optionally, filter/normalize the results before comparing. Specify the output directoryes to store added and removed warnings in. Click Diff.

In the Filter tab, specify directory that also contains results in XML format and specify the filter substring. Specify output directory to store the filtering results in. Click Filter.
