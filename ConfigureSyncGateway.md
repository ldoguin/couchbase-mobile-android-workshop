# Sync Gateway

## How to Configure and Launch Sync Gateway

First thing you need to do is to download Sync Gateway at the following URL: http://www.couchbase.com/downloads

Once you have downloaded it, simply uncompress it where your want it to stay. There is no further installation step.

To run it, launch the ./bin/sync_gateway executable. This should run it with a default configuration. Stop it with a simple CTRL-C.

This workshop requires some custom configuration. It can be stored in a JSON file given as argument to Sync Gateway.

Here's the appropriate configuration:


    {
    	"log": ["CRUD", "HTTP+"],
    	"databases": {
    		"db": {
    			"server": "walrus:",
    			"sync": `
    function(doc){
    	channel(doc.channels);
    }`,
    			"users": {
    				"admin": {
    					"admin_channels": ["all"],
    					"admin_roles": ["powerUser"],
    					"password": "foo"
				    },
    				"GUEST": {"disabled": false, "admin_channels": ["*"] }
    			},
	    		"roles": {
    				"powerUser": {"admin_channels": ["admChan"]}
			    }
		    }
	    }
    }


If you have created a file called config.json with the previous content, simply type 

    ./bin/sync_gateway config.json


## How to Add Presentation Document with the REST API

You need to POST the following JSON document:

      {
        "created_at": 1424100089320,
        "presentationAbstract": "presentation abstract",
        "title": "title 2",
        "type": "presentation"
      }

to your Sync Gateway database. If you use the provided configuration file and run Sync Gateway on your laptop, the URL should be http://localhost:4984/db/

An example with the commandLine too curl would look like this:

    curl  -X POST http://localhost:4984/db/  -H "Content-Type: application/json"  -d '{"created_at": 1424100089320,"presentationAbstract": "presentation abstract","title": "title 2","type": "presentation"}'

If it worked the server should give a JSON response like this:

    {"id":"e4ed2302b1160205393fb95751786692","ok":true,"rev":"1-25dcda6d892aed58989e72bc999578aa"}


