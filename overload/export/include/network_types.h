#ifndef NETWORK_TYPES_H
#define NETWORK_TYPES_H

#include <time.h>

struct peernode_s
{
	char *host;
	int   port;
};

typedef struct peernode_s peernode_t;

struct webnode_s
{
	char   *url;
	time_t request_time;
};

typedef struct webnode_s webnode_t;

peernode_t *peernode_new(const char *host, int port);
webnode_t  *webnode_new (const char *url, time_t request_time);


#endif
