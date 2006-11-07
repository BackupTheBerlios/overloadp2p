#include <network_types.h>

#include <stringutils.h>

peernode_t *peernode_new(const char *host, int port)
{
	if (port < 1 || port > 65535) return NULL;
	
	size_t host_len = strlen(host);
	if (host_len < 3) return NULL;
	
	peernode_t *result = malloc(sizeof(peernode_t));
	result->host = strdup(host);
	result->port = port;
	return result;
}

webnode_t  *webnode_new (const char *url, time_t request_time)
{
	if (request_time < 0 || request_time > time(0)) return NULL;
	
	size_t url_len = strlen(url);
	if (url_len < 10) return NULL;
	
	char *url_lower = string_to_lower(url);
	if (!string_starts_with(url_lower, "http://"))
	{
		free(url_lower);
		return NULL;
	}
	
	free(url_lower);
	
	if (string_contains(url, "?")) return NULL;
	
	webnode_t *result = malloc(sizeof(webnode_t));
	result->url = strdup(url);
	result->request_time = request_time;
	return result;
}
