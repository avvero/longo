import com.avvero.longo.LogItemHandler

/*
 Import your handler classes

import your.package.ChatMeteorHandler
import your.package.ForumMeteorHandler
import your.package.SimpleMeteorHandler
*/

/*
 defaultMapping is used by _Events.groovy to create atmosphere-meteor-decorators.xml
 and update sitemesh.xml in web-app/WEB-INF.

*/
defaultMapping = "/log/*"

/*
 The defaultInitParams below are added to each MeteorServlet defined above
 unless the servlet has specified its own initParams map.
 See http:pastehtml.com/view/cgwfei5nu.html for details.
*/

defaultInitParams = [
		"org.atmosphere.cpr.broadcasterCacheClass": "org.atmosphere.cache.UUIDBroadcasterCache",
		"org.atmosphere.cpr.AtmosphereInterceptor": """
			org.atmosphere.client.TrackMessageSizeInterceptor,
			org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor,
			org.atmosphere.interceptor.HeartbeatInterceptor
		"""
]

servlets = [
        LogItemServlet: [
                className: "com.avvero.longo.DefaultMeteorServlet",
                mapping: "/flow/log/*",
                handler: LogItemHandler,
                initParams: defaultInitParams
        ]
]
