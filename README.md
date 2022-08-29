A Trie based solution to finding the most likely completion of an English word according to how common each word is.

The program works by storing the number of appearances of each word in the Project Gutenberg library, then returning the most common words starting with the given string.

The search for possible completions is done using a trie data structure. The trie nodes also store a frequency score in each end node. These scores are used to rank how likely each word is to be next.