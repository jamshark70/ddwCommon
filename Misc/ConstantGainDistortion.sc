// hjh 2022 - gplv3

ConstantGainDistortion {
	*ar { |sig, preamp = 1, distFunc(_.tanh)|
		var distorted = distFunc.value(sig * preamp);
		var ampBefore = Amplitude.ar(sig, 0.1, 1);
		var ampAfter = Amplitude.ar(sig, 0.1, 1);

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}

	*kr { |sig, preamp = 1, distFunc(_.tanh)|
		var distorted = distFunc.value(sig * preamp);
		var ampBefore = Amplitude.kr(sig, 0.1, 1);
		var ampAfter = Amplitude.kr(sig, 0.1, 1);

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}
}
