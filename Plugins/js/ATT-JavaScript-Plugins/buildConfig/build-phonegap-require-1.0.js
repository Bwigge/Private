({
	
	baseUrl: "../src",
    out: "../builds/att-phonegap-require-min-1.0.js",
    has: {
    	titanium: true,
    	phonegap: true,
        XMLHttpRequest: true,
        jquery: true,
        
        //Use this value for debuging
        DEBUG: false,
        
        //Packages to import
        ADS: true,
        CMS: true,
        DC: true,
        IMMN: true,
        PAYMENT: true,
        MMS: true,
        SMS: true,
        SPEECH: true,
        TTS: true,
        TL: true,
        WAP: false,
        STTC: false
    },
    
    optimize: 'uglify2',
    //To remove minimizing, uncomment this next line:
    //optimize: 'none',
    
    name: "../lib/almond",
    include: ['extensions/phonegap', 'wrappers/wrapper-1.0'],
    wrap: {
    	start: 'define("att",["exports"],function(e){',
    	end: 'var r=require;r("extensions/phonegap");e.ATT=r("wrappers/wrapper-1.0");});'
    }
})