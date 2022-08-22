// hjh 2022 - gplv3

ConstantGainDistortion {
	*ar { |sig, preamp = 1, distFunc(_.tanh), attack = 0.02, decay = 0.25|
		var distorted = distFunc.value(sig * preamp);
		var ampBefore = Amplitude.ar(sig, attack, decay);
		var ampAfter = Amplitude.ar(distorted, attack, decay);

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}

	*kr { |sig, preamp = 1, distFunc(_.tanh), attack = 0.02, decay = 0.25|
		var distorted = distFunc.value(sig * preamp);
		var ampBefore = Amplitude.kr(sig, attack, decay);
		var ampAfter = Amplitude.kr(distorted, attack, decay);

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}
}
