#!/usr/bin/env /usr/local/groovy-2.1.5/bin/groovy

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Test {
	
	public static final String DESTINATION_HOST = "c9980.fm.co.za";
	public static final String DESTINATION_DIRECTORY = "/home/ucsretail/jobs";
	public static final String DESTINATION_USER = "root";
	
	public static String jobName = null;
	public static File sqlFile = null;
	public static String sqlFileName = null;
	public static Scanner input = null;
	
	private static String description = null;
	private static String authorName = null;
	private static String dateGenerated = null;
	
	private static String outputScriptName = null;
	private static FileWriter writer = null;
	
	public static void main(String[] args) throws IOException {
                
                println("Content-Type : text/plain");
            
		// read in the arguments
                sqlFile = new File("tempSqlFile.txt");
                
                if (sqlFile == null) {
                    println("Error: We could not process your SQL queries.");
                    System.exit(0);
                }
		
		jobName = args[0];
                if (jobName == null) {
                    println("Error: No job name could be located.");
                     System.exit(0);
                }
		
		// check if the sql file exists on the
		// file system
		
		if (!sqlFile.exists() || sqlFile.isDirectory()) {
                        println("Error: We could not process your SQL queries.");
			System.exit(1); 
		}
		
		// check if the job name is valid
		if (jobName.length() < 5 || jobName.startsWith("job.") || jobName.endsWith(".sh")) {
			println("The name of the job may not start with 'job.', " + 
								"or end with .sh extensions. A plain one word for a " + 
								"would be mostly appreciated."); 
			System.exit(1); 
		}
	
		
		// prompt for the author name
		authorName = arg[1];
                if (authorName == null) {
                    println("Error: No author name could be located");
                    System.exit(0);
                }
		
		// prompt for the description
		description = arg[2];
		
		System.out.println();
	
                println("Processing your SQL job, please wait...");

        
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
		
		println("Script built, please find it at: ");
		println(outputScriptName);
	
		println("");
		
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

