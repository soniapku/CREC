	if (typeBinding == IntBinding) {
		switch (resolvedPosition) {
			case 0 :
				this.iload_0();
				break;
			case 1 :
				this.iload_1();
				break;
			case 2 :
				this.iload_2();
				break;
			case 3 :
				this.iload_3();
				break;
			//case -1 :
			// internal failure: trying to load variable not supposed to be generated
			//	break;
			default :
				this.iload(resolvedPosition);
		}
		return;
	}
