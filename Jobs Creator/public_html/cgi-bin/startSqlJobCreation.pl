#!/usr/bin/perl

# using the CGI core - read the parameters
use CGI;

my $jobName = param('jobName');
my $author = param('authorName');
my $description = param('jobDescription');
my $sqlQueries = param('sqlQueries');

# write the query to a temp file
open (SQLFILE, '>> tempSqlFile.txt');
print SQLFILE $sqlQueries;
close (SQLFILE); 

# run the groovy script
system("createSqlUpdateJob", $jobName, $author, $description);



