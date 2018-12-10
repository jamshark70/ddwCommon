
NumericRange {
	var	<>lo, <>hi, <>spec;

	*new { arg lo, hi, spec;
		^super.newCopyArgs(lo, hi, spec.asSpec)
	}

	rrand { ^rrand(lo, hi) }
	inrange { |num| ^num.inclusivelyBetween(lo, hi) }
	intersects { |that|
		if(lo < that.lo) {
			^hi >= that.lo
		} {
			^lo <= that.hi
		}
	}
	includes { |that|
		^that.lo.inclusivelyBetween(lo, hi) and: {
			that.hi.inclusivelyBetween(lo, hi)
		}
	}

	range01_ { arg l, h;
		lo = spec.map(l);
		hi = spec.map(h);
	}

	range_ { arg l, h;
		lo = l;
		hi = h;
	}

	lo01 { ^spec.unmap(lo) }
	hi01 { ^spec.unmap(hi) }

	guiClass { ^NumericRangeGui }

	storeArgs { ^[lo, hi, spec] }
	printOn { |stream|
		stream << "NumericRange(" << lo << ", " << hi;
		if(spec.minval != 0 or: { spec.maxval != 1 or: { spec.warp.isKindOf(LinearWarp).not } }) {
			stream <<< spec;
		};
		stream << ")"
	}
}
