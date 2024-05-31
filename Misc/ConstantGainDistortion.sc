// hjh 2022 - gplv3

ConstantGainDistortion {
	*ar { |in, preamp = 1, distFunc(_.tanh), rmsSize = 512|
		var distorted = distFunc.value(in * preamp);

		var ampBefore = (RunningSum.ar(in.squared.asArray.sum, rmsSize) / rmsSize).sqrt;
		var ampAfter = (RunningSum.ar(distorted.squared.asArray.sum, rmsSize) / rmsSize).sqrt;

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}

	*kr { |in, preamp = 1, distFunc(_.tanh), rmsSize = 512|
		var distorted = distFunc.value(in * preamp);
		var size = rmsSize / BlockSize.ir;

		var ampBefore = (RunningSum.kr(in.squared.asArray.sum, size) / size).sqrt;
		var ampAfter = (RunningSum.kr(distorted.squared.asArray.sum, size) / size).sqrt;

		^distorted * min(10, (ampBefore / max(0.01, ampAfter)))
	}
}
