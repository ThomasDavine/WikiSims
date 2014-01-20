WikiSims 

Calculate semantic similarity between terms using Wikipedia information.
WikiSims posts queries to Wikipedia dump or crawls Wikipedia online, handles disambiguations and redirects, parses Wikipedia pages related to each term and calculates cosine and Jaccard similarity between tf-idf vectors.

eg: "Obama" "US President" "U2 singer" "actor"
WikiSims will search the terms in Wikipedia, disambiguate "Obama" with "Barack Obama", follow the suggestion "Bono", extract and parse the text articles, break it into tokens, calculate the TermFrequency - Inverse Document Frequency matrix and calculate similarity between words.


author: Tommaso Moretti



