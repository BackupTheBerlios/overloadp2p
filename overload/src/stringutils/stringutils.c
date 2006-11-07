#include <stringutils.h>

size_t string_index_of(const char *s, const char *what, char *found)
{
	size_t s_len, what_len;
	if (s_len < what_len)
	{
		*found = 0;
		return 0;
	}
	
	if (s_len == what_len)
	{
		if (strcmp(s, what) == 0)
		{
			*found = 1;
			return 0;
		}
		else
		{
			*found = 0;
			return 0;
		}
	}
	
	size_t s_pos;
	char *buf = malloc(what_len + 1);
	memset(buf, '\0', what_len + 1);
	for(s_pos = 0; s_pos < s_len; ++s_pos)
	{
		strncpy(buf, s + s_pos, what_len);
		buf[what_len] = '\0';
		if (strcmp(buf, what) == 0)
		{
			free(buf);
			*found = 1;
			return s_pos;
		}
	}
	
	*found = 0;
	return 0;
}

inline char char_upper(char c)
{
	if (c < 'a' || c > 'z') return c;
	char diff = 'a' - 'A';
	return c - diff;
}

inline char char_lower(char c)
{
	if (c < 'A' || c > 'Z') return c;
	char diff = 'a' - 'A';
	return c + diff;
}

void string_part_lower(char *s, size_t from, size_t count)
{
	size_t s_len, i;
	s_len = strlen(s);
	
	if (s_len < from - 1) return;
	
	for(i = 0; i < count; ++i)
	{
		if (from + i >= s_len) break;
		s[from + i] = char_lower(s[from + i]);
	}
}

char *string_to_lower(const char *s)
{
	size_t s_len, i;
	char diff;
	char *result = strdup(s);
	s_len = strlen(s);
	diff = 'a' - 'A';
	
	for(i = 0; i < s_len; ++i)
		if (result[i] >= 'A' && result[i] <= 'Z')
			result[i] += diff;
	return result;
}

char *string_to_upper(const char *s)
{
	size_t s_len, i;
	char diff;
	char *result = strdup(s);
	s_len = strlen(s);
	diff = 'a' - 'A';
	
	for(i = 0; i < s_len; ++i)
		if (result[i] >= 'A' && result[i] <= 'Z')
			result[i] -= diff;
	return result;
}

char string_contains (const char *s, const char *what)
{
	size_t s_len, what_len;
	s_len = strlen(s);
	what_len = strlen(what);
	
	if (s_len < what_len) return 0;
	if (s_len == what_len) return strcmp(s, what) == 0;
	
	size_t i;
	for(i = 0; i < s_len - what_len; ++i)
		if (strncmp(s + i, what, what_len) == 0)
			return 1;
	return 0;
}

char string_ends_with(const char *s, const char *with)
{
	size_t s_len, with_len;
	s_len = strlen(s);
	with_len = strlen(with);
	if (s_len < with_len) return 0;
	if (s_len == with_len) return strcmp(s, with) == 0;
	
	return strncmp(s + s_len - with_len, with, with_len) == 0;
}

char string_starts_with(const char *s, const char *with)
{
	size_t s_len, with_len;
	s_len = strlen(s);
	with_len = strlen(with);
	
	if (s_len < with_len) return 0;
	if (s_len == with_len) return strcmp(s, with) == 0;
	
	return strncmp(s, with, with_len) == 0;
}

char **string_split(const char *s, const char *d, size_t *parts_count)
{
	size_t count, s_len, d_len, s_pos, part_pos;
	char **dirty_result, **result;
	char *part, *buf;
	
	s_len = strlen(s);
	d_len = strlen(d);
	
	if (s_len == 0 || d_len == 0 || s_len == d_len)
	{
		char *s_cpy = strdup(s);
		result = malloc( sizeof(char*) );
		result[0] = s_cpy;
		*parts_count = 1;
		return result;
	}
	
	if (strcmp(s,d) == 0)
	{
		result = malloc( sizeof(char*) );
		result[0] = '\0';
		*parts_count = 1;
		return result;
	}
	
	part = malloc(s_len + 1);
	memset(part, '\0', s_len + 1);
	
	buf = malloc(d_len + 1);
	
	dirty_result = malloc(sizeof(char*) * s_len);
	memset(dirty_result, '\0', s_len);
	
	count = 0;
	s_pos = 0;
	part_pos = 0;
	
	while(s_pos < s_len - d_len)
	{
		strncpy(buf, s + s_pos, d_len);
		buf[d_len] = '\0';
		if (strcmp(buf, d) == 0)
		{
			part[part_pos] = '\0';
			dirty_result[count] = strdup(part);
			++count;
			s_pos += d_len;
			part_pos = 0;
		}
		else
		{
			strncpy(part + part_pos, buf, 1);
			++part_pos;
			++s_pos;
		}
	}
	
	//123123123 3
	strcpy(buf, s + s_pos);
	if (strcmp(buf, d) != 0)
		strcpy(part + part_pos, buf);
		
	dirty_result[count] = part;
	++count;
	*parts_count = count;
	
	result = malloc(sizeof(char*) * count);
	
	size_t i;
	for(i = 0; i < count; ++i)
	{
		result[i] = dirty_result[i];
	}
	
	free(dirty_result);
	free(buf);
	
	return result;
}
