({
	baseUrl: "../src",
    out: "../builds/att-worklight-min.js",
    has: {
        titanium: false,
        phonegap: false,
        XMLHttpRequest: true,
        jquery: false,
        
        //Packages to import
        ADS: false,
        CMS: false,
        DC: false,
        IMMN: true,
        PAYMENT: false,
        MMS: true,
        SMS: false,
        SPEECH: false,
        TTS: false,
        TL: false,
        WAP: false
    },
    //optimize: 'none', //Uncomment this line to remove minimization
    
    name: '../lib/almond',
    include: [ 'extensions/worklight' ],
    wrap: {
    	start: 'var attRaw = (function(){',
    	end: 'return require("extensions/worklight");})();'
    }
})