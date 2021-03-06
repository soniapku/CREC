		if (visibleParametersAnnotationsCounter != 0) {
			if (contentsOffset + 7 >= contents.length) {
				resizeContents(7);
			}
			int attributeNameIndex =
				constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleParameterAnnotationsName);
			contents[contentsOffset++] = (byte) (attributeNameIndex >> 8);
			contents[contentsOffset++] = (byte) attributeNameIndex;
			int attributeLengthOffset = contentsOffset;
			contentsOffset += 4; // leave space for the attribute length

			contents[contentsOffset++] = (byte) argumentsLength;
			visibleLoop: for (int i = 0; i < argumentsLength; i++) {
				if (contentsOffset + 2 >= contents.length) {
					resizeContents(2);
				}
				if (visibleParametersAnnotationsCounter == 0) {
					contents[contentsOffset++] = (byte) 0;
					contents[contentsOffset++] = (byte) 0;					
				} else {
					final int numberOfVisibleAnnotations = annotationsCounters[i][VISIBLE_INDEX];
					contents[contentsOffset++] = (byte) (numberOfVisibleAnnotations >> 8);
					contents[contentsOffset++] = (byte) numberOfVisibleAnnotations;
					if (numberOfVisibleAnnotations != 0) {
						Argument argument = arguments[i];
						Annotation[] annotations = argument.annotations;
						for (int j = 0, max = annotations.length; j < max; j++) {
							Annotation annotation = annotations[j];
							if (isRuntimeVisible(annotation)) {
								generateAnnotation(annotation, annotationAttributeOffset);
								if (contentsOffset == annotationAttributeOffset) {
									break visibleLoop;
								}
								visibleParametersAnnotationsCounter--;
							}
						}
					}
				}
			}
			if (contentsOffset != annotationAttributeOffset) {
				int attributeLength = contentsOffset - attributeLengthOffset - 4;
				contents[attributeLengthOffset++] = (byte) (attributeLength >> 24);
				contents[attributeLengthOffset++] = (byte) (attributeLength >> 16);
				contents[attributeLengthOffset++] = (byte) (attributeLength >> 8);
				contents[attributeLengthOffset++] = (byte) attributeLength;			
				attributesNumber++;
			} else {
				contentsOffset = annotationAttributeOffset;
			}
		}
