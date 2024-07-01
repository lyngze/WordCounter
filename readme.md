# Word counter
An application to count words from a designated directory containing text files. A report is outputted with a file for each starting letter in the counted words.
An optional list of excluded words can also be supplied which will be omitted from the general word count.

## Get started
To use the application either run main from your IDE or build a jar and run from command line.

java -jar WordCounter.jar directory excludedWordsFile

Directory (required): Must specify a directory including text files with words to be counted.

ExcludedWordsFile (optional): Can be used to specify a file containing words to be excluded from the word count. Excluded words will still be registered, but in a separate list and outputtet to a separate "excludedWords" file.