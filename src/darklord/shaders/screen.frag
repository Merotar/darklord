uniform int resX, resY;
uniform sampler2D tex;
//uniform vec4 gl_Color;
	
	float rand(vec2 n)
	{
	  return 0.5 + 0.5 * 
	     fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);
	}

	#define NUM_SAMPLES 10

	const float exposure = 1.0;
	const float decay    = 0.895;
	const float density  = 1.5;
	const float weight   = 0.025;
	
	void main()
	{
		
//		vec4 color;
//		
////		color = getColor();// + gl_Color;
//		color = texture2D(tex,gl_TexCoord[0].st);
//		color *= gl_Color.a;
//		
////		if (color.z >0.6) color *= 1.5*rand(gl_FragCoord.xy);
//		
////		color.z = (gl_FragCoord.x*gl_FragCoord.x*gl_FragCoord.x+gl_FragCoord.y+gl_FragCoord.y)/(resX*resX*resX);
//		
//		gl_FragColor = color;
		
	    vec4 LightColor = vec4(0.2, 0.2, 0.2, 0.1);
	    vec2 LightProjectedCameraSpacePos = vec2(50., 50.);

	    vec2 deltaTextCoord = vec2(gl_TexCoord[0].st - LightProjectedCameraSpacePos.xy );
	    vec2 textCoord = gl_TexCoord[0].st;
	    deltaTextCoord *= density / float(NUM_SAMPLES) ;
	    float illuminationDecay = 1.0;


	    vec4 sample;
	    vec4 ScatteringColor = vec4(0.0, 0.0, 0.0, 0.0);

	   
	    for(int i=0; i < NUM_SAMPLES ; i++)
	    {
	        textCoord -= deltaTextCoord;

//	        if(texture2D(tex, textCoord).w == -1.0)
	        {
	            sample = LightColor;
	            sample *= illuminationDecay;
	            ScatteringColor += sample;          
	        }
	        
	        
	        illuminationDecay *= decay;
	    }

	    ScatteringColor *= exposure;

	    
	    //gs_FragColor = ScatteringColor;
	    gl_FragColor = texture2D(tex, gl_TexCoord[0].st)+ScatteringColor;
	}