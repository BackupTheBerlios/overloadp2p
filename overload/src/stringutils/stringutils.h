#ifndef STRINGUTILS_H
#define STRINGUTILS_H

#include <string.h>
#include <stdlib.h>

/** Splits S by D.
		@param s String to split
		@param d Delimiter string
		@param parts_count pointer into which the count of resulting parts will be
		written
		@return Array containing splitted parts*/
char **string_split(const char *s, const char *d, size_t *parts_count);
char string_starts_with(const char *s, const char *with);
char string_ends_with(const char *s, const char *with);
char string_contains (const char *s, const char *what);
char *string_to_lower(const char *s);
char *string_to_upper(const char *s);
void string_part_lower(char *s, size_t from, size_t count);
inline char char_lower(char c);
inline char char_upper(char c);
size_t string_index_of(const char *s, const char *what, char *found);

#endif
