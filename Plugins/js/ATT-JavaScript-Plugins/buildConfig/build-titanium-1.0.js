({
	baseUrl: "../src",
    out: "../builds/att-titanium-min-1.0.js",
    has: {
        titanium: true,
        phonegap: false,
        XMLHttpRequest: false,
        jquery: false,
        
        DEBUG: false,
        
        //Packages to import
        ADS: false,
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
        STTC: true
    },
    //optimization isn't needed because titanium optimizes the build for us
    optimize: 'none', //Uncomment this line to remove minimization
    
    name: '../lib/almond',
    include: [ 'extensions/titanium', 'wrappers/wrapper-1.0' ],
    wrap: {
    	start: 'var tiRequire=require;exports.ATT=(function(){',
    	end: 'var r=require;r("extensions/titanium");return r("wrappers/wrapper-1.0");})();'
    }
})