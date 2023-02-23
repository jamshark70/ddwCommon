ValidatingIdentityDictionary : IdentityDictionary {
	var <>validator, <>putFallback;

	put { |key, value|
		if(validator.(value, key) ?? { true }) {
			super.put(key, value);
		} {
			putFallback.(key, value, this);
		}
	}

	putGet { |key, value|
		var prev = this.at(key);
		this.put(key, value);
		^prev
	}
}
