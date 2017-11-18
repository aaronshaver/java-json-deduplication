# java-json-deduplication

Outputs new JSON file with dupes from original removed, as well as logging which records and fields changed

## Usage

To run the program, open a terminal and then:

    java -jar java-json-deduplication.jar your_file.json

Then find the deduplicated.json and deduplication_log_<datetime>.log file upon completion

## Requirements

1. Data from newest-date entry preferred
2. Dupe _id and dupe email fields count as dupes, both must be unique. Dupes elsewhere don't matter.
3. If a dupe is found and both records have identical entryDate, entry last in the list is preferred
4. No need to worry about large files, program allowed to do everything in memory
5. Provide log of changes including: source record, output record, individual field changes (from/to)
