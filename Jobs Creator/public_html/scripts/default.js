var DESTINATION_HOST = "c9980.fm.co.za";
var DESTINATION_DIRECTORY = "/home/ucsretail/jobs";
var String DESTINATION_USER = "root";

var jobName = null;
var description = null;
var authorName = null;
var dateGenerated = null;

var outputScriptName = null;


function createJobFile() {

        var important = "";
        important +=  "PLEASE CONSIDER THESE GUIDELINES WHEN USING THIS JOB CREATOR:\n\n";
        important +=  "Rules:\n"
        important +=  "1. The SQL file must have each query on a single line - each ending with a semi-colon.\n";
        important +=  "2. The SQL file may not contain any empty lines - even at the end of the file.\n";
        important +=  "3. The job name must be a friendly name - with no spaces and: \n";
        important +=  "               (i) Not start with 'job.'\n";
        important +=  "               (ii) Not end with '.sh' or any file extensions.\n";
        important +=   "\n";
        important +=  "Copy your job to c9980:/home/ucsretail/jobs.\n";
        important +=  "NB: TEST YOUR JOB BEFORE SHIPPING IT TO c9980...\n\n";
        important +=  "Click OK when ready.";

        JOptionPane.showMessageDialog(null, important);

        // read in the arguments
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Browse the file with your SQL queries:"); 
        if (chooser.showDialog(null, "Accept") == JFileChooser.APPROVE_OPTION) {
            sqlFile = chooser.getSelectedFile();
        }

        if (sqlFile == null) {
            JOptionPane.showMessageDialog(null, "You must selected a SQL file before you continue.");
            System.exit(0);
        }

        jobName = JOptionPane.showInputDialog("Enter a friendly job name: (NB: remember the rules for doing this)");

        // check if the sql file exists on the
        // file system

        if (!sqlFile.exists() || sqlFile.isDirectory()) {
                System.out.println("The sql file specified does not exist on the file system.");
                System.exit(1); 
        }

        else 
                System.out.println("SQL FILE FOUND: " + sqlFileName);

        // check if the job name is valid
        if (jobName.length() < 5 || jobName.startsWith("job.") || jobName.endsWith(".sh")) {
                System.out.println("The name of the job may not start with 'job.', " + 
                                                        "or end with .sh extensions. A plain one word for a " + 
                                                        "would be mostly appreciated."); 
                System.exit(1); 
        }

        else
                System.out.println("JOB NAME: " + jobName);


        // prompt for the author name
        authorName = JOptionPane.showInputDialog("Enter the author of the script:");

        // prompt for the description
        description = JOptionPane.showInputDialog("Type a short description for the script: ");

        System.out.println("Please wait as the script is being built...");
        System.out.println();

        // generate the date of the script
        dateGenerated = new SimpleDateFormat("MMMM dd, yyyy", Locale.UK).format(new Date());

        // start writing output script to /tmp
        outputScriptName = "/tmp/job." + jobName + ".sh";
        File scriptFile = new File(outputScriptName);
        if (scriptFile.exists())
                scriptFile.delete();

        scriptFile.createNewFile();

        writer = new FileWriter(scriptFile, true);

        // build the header of the script
        buildScriptHeader(writer, authorName, description, dateGenerated);

        // build the required functions of the script
        addInitialiseVariablesFunction(writer);
        addCreateSqlAndLogFilesFunction(writer);
        addRunCommandLineSqlUpdateFunction(writer);
        addDetermineResultsAndExitStatusFunction(writer);
        addUucpResultsToc9980Function(writer);
        addCustomSqlUpdateFunction(writer, sqlFile);
        addMainScriptSection(writer);

        writer.close();

        System.out.println("Script built, please find it at: ");
        System.out.println(outputScriptName);

        System.out.println();

        System.exit(0);
}

private static void buildScriptHeader(FileWriter writer,
                String authorName, String description, String dateGenerated) 
        throws IOException {

        writer.append("#!/bin/bash  \n");
        writer.append("set -vx \n");
        writer.append("\n");
        writer.append("# -----------------------------------\n");
        writer.append("# ABOUT THIS JOB \n");
        writer.append("# -----------------------------------\n");
        writer.append("\n");
        writer.append("# Author: " + authorName + " \n");
        writer.append("# Date: " + dateGenerated + " \n");
        writer.append("# \n");
        writer.append("# Description: " + description + "\n");
        writer.append("\n");
        writer.append("# -----------------------------------\n");
        writer.append("# FUNCTIONS \n");
        writer.append("# -----------------------------------\n");
        writer.append("\n");

}

private static void addInitialiseVariablesFunction(FileWriter writer) throws IOException {
        writer.append("# INITIALISATION - required  \n");
        writer.append("function initialiseVariables() {  \n");
        writer.append("\tBRANCH=`uuname -l |cut -c2-5`  \n");
        writer.append("\tNAME=`basename \$0 | cut -d. -f2`  \n");

        writer.append("\tRESULT=/tmp/\$NAME.\${BRANCH}  \n");
        writer.append("\tSTATUS=0  \n");
        writer.append("\tDISPLAY=\"\"  \n");

        writer.append("\tANT=\"/usr/local/ant/bin/ant\"  \n");
        writer.append("\tPOSTGRES=\"/usr/local/pgsql/bin/psql\"  \n");
        writer.append("\tMANUAL_SQL_LOG_FILE=\"/tmp/manualSwitchedSQL.log\"  \n");
        writer.append("\tSQL_FILE=\"/tmp/\${BRANCH}_\$NAME.sql\"  \n");

        writer.append("\tCLASS_NAME=\"ucs.instore.ejb.command.CommandLineBuildSqlUpdate\"  \n");
        writer.append("\tSUCCESSFULLY_PROCESSED_MSG=\"Successfully processed SQL file\"  \n");
        writer.append("}\n\n");
}

private static void addCreateSqlAndLogFilesFunction(FileWriter writer) throws IOException {

        writer.append("# CREATES THE SQL AND LOG FILES  - required  \n");
        writer.append("function createSqlAndLogFiles() {  \n");
        writer.append("\t> \${SQL_FILE}  \n");
        writer.append("\t> \${MANUAL_SQL_LOG_FILE}  \n");
        writer.append("}  \n\n");
}

private static void addRunCommandLineSqlUpdateFunction(FileWriter writer) throws IOException {

        writer.append("# RUNS THE COMMAND LINE SQL UPDATE - required  \n");
        writer.append("function runCommandLineSqlUpdate() {  \n");
        writer.append("\tcd /home/ucsretail/furniture  \n");
        writer.append("\t\${ANT} runCommandLineBuildSQLUpdate -Dclass=\${CLASS_NAME} -DSQLUpdateLogFile=\$MANUAL_SQL_LOG_FILE  -Darg1=\${SQL_FILE} | grep \"\${SUCCESSFULLY_PROCESSED_MSG}\"  \n");
        writer.append("\treturn \$?  \n");
        writer.append("}  \n\n");
}

private static void addDetermineResultsAndExitStatusFunction(FileWriter writer) throws IOException {

        writer.append("# DETERMINES THE EXIT STATUS AND GENERATES A RESULT MESSAGE - required  \n");
        writer.append("function determineExitStatusResults() {  \n");

        writer.append("\tEXIT_STATUS=\$1  \n");

        writer.append("\tif [ \${EXIT_STATUS} -ne 0 ]; then      \n");
        writer.append("\techo \"STATUS: 1\" > \${RESULT}         \n");
        writer.append("\techo \"DISPLAY: SQL Job Failed.\" >> \${RESULT}   \n");
        writer.append("\telse  \n");
        writer.append("\techo \"STATUS: 0\" > \${RESULT}         \n");
        writer.append("\techo \"DISPLAY: SQL Job ran successfully.\" >> \${RESULT}  \n");
        writer.append("\tfi     \n");

        writer.append("}  \n\n");

}

private static void addUucpResultsToc9980Function(FileWriter writer) throws IOException {

        writer.append("# SENDS BACK THE RESULTS TO c9980 - required  \n");
        writer.append("function uucpResultsToc9980() {  \n");
        writer.append("\tchmod 777 \$RESULT  \n");
        writer.append("\tuucp \$RESULT c9980\\!/home/ucsretail/jobs/results/\$NAME/  \n");
        writer.append("}  \n\n");
}

private static void addCustomSqlUpdateFunction(FileWriter writer, File file) throws IOException {

        int lineCount = 0;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        boolean EOF = false;

        writer.append("function updateCustomSql() {  \n");
        while (!EOF) {

                line = reader.readLine();
                if (line != null) {
                        lineCount++;

                        line = line.trim();

                        if (line.length() > 0 && line.endsWith(";")) 
                                writer.append("\techo \"" + line + "\" >> \${SQL_FILE}  \n");
                        else
                                System.out.println("Skipped line no: " + lineCount + " [" + line + "] - line not according to rules.");
                }
                else
                        EOF = true;
        }

        writer.append("}  \n\n");
        reader.close();
}

private static void addMainScriptSection(FileWriter writer) throws IOException {
        writer.append("#----------------------------------------  \n");
        writer.append("# SCRIPT / JOB STARTS HERE  \n");
        writer.append("#----------------------------------------  \n");
        writer.append("\n");
        writer.append("# initialise  \n");
        writer.append("initialiseVariables  \n");
        writer.append("\n");
        writer.append("# create sql file and log file  \n");
        writer.append("createSqlAndLogFiles  \n");
        writer.append("\n");
        writer.append("updateCustomSql  \n");
        writer.append("\n");
        writer.append("# run the sql update  \n");
        writer.append("runCommandLineSqlUpdate  \n");
        writer.append("\n");
        writer.append("# determine the exit status  \n");
        writer.append("OUTPUT=\$?  \n");
        writer.append("determineExitStatusResults \${OUTPUT}  \n");
        writer.append("\n");
        writer.append("# send back the results to c9980  \n");
        writer.append("uucpResultsToc9980  \n");
        writer.append("\n");
        writer.append("# terminate the job  \n");
        writer.append("exit 0  \n");

 }
}