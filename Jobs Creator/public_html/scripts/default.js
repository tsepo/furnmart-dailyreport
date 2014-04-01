function createJobFile() {
    
    var jobNameTextBox = document.getElementById("jobName");
    var authorNameTextBox = document.getElementById("authorName");
    var jobDescriptionTextBox = document.getElementById("jobDescription");
    var sqlQueriesTextBox = document.getElementById("sqlQueries");
    
    // validate the entries
    var jobName = jobNameTextBox.value.trim();
    if (jobName == null || jobName.length < 5 || jobName.indexOf(" ") >= 0 || 
            jobName.indexOf("job.") >= 0 || jobName.indexOf(".sh") >= 0) {
        alert("Please enter a job name, as per rules.");
        jobNameTextBox.select();
        return false;
    }
    
    var author = authorNameTextBox.value.trim();
    if (author == null || author.length < 5) {
        alert("You need to provide the author of this job.");
        authorNameTextBox.select();
        return false;
    }
    
    var description = jobDescriptionTextBox.value;
    if (description == null || description.length < 5) {
        alert("Type a description to indicate what this job accomplishes.");
        jobDescriptionTextBox.select();
        return false;
    }
    
    var queries = sqlQueriesTextBox.value;
    if (queries == null || queries.length < 5) {
        alert("Type your SQL query.");
        sqlQueriesTextBox.select();
        return false;
    }
    
    return true;
    
}