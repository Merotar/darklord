uniform int resX, resY;
uniform sampler2D tex;
	
	float rand(vec2 n)
	{
	  return 0.5 + 0.5 * 
	     fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);
	}

	vec4 getColor()
	{
		vec4 color;
		float borderX = 0.3;
		float borderY = 0.3;
		
		color = texture2D(tex,gl_TexCoord[0].st);
		
		if (gl_FragCoord.x/resX < borderX)
		{
			color *= gl_FragCoord.x/(resX*borderX);
//			return color;
		}
		
		if (gl_FragCoord.x/resX > (1-borderX))
		{
			color *= (resX-gl_FragCoord.x)/(resX*borderX);
//			return color;
		}
		
		if (gl_FragCoord.y/resY < borderY)
		{
			color *= gl_FragCoord.y/(resY*borderY);
//			return color;
		}
		
		if (gl_FragCoord.y/resY > (1-borderY))
		{
			color *= (resY-gl_FragCoord.y)/(resY*borderY);
//			return color;
		}
		
//		color = texture2D(tex,gl_TexCoord[0].st);
		return color;
	}

	void main()
	{
		
		vec4 color;
		
		color = getColor();
//		color = texture2D(tex,gl_TexCoord[0].st);
		
//		if (color.z >0.6) color *= 1.5*rand(gl_FragCoord.xy);
		
//		color.z = (gl_FragCoord.x*gl_FragCoord.x*gl_FragCoord.x+gl_FragCoord.y+gl_FragCoord.y)/(resX*resX*resX);
		
		gl_FragColor = color;
	}