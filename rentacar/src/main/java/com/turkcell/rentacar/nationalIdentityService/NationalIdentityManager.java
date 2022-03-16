package com.turkcell.rentacar.nationalIdentityService;


public class NationalIdentityManager implements NationalIdentityService{

	@Override
	public boolean checkNationalIdentity(String nationalIdentity) {
		int[] numbers = parcala(nationalIdentity);

		if (numbers != null) {
			boolean kosul1 = (numbers[0] + numbers[1] + numbers[2] + numbers[3] + numbers[4] + numbers[5] + numbers[6]
					+ numbers[7] + numbers[8] + numbers[9]) % 10 == numbers[10];
			boolean kosul2 = (((numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8]) * 7)
					+ ((numbers[1] + numbers[3] + numbers[5] + numbers[7]) * 9)) % 10 == numbers[9];
			boolean kosul3 = ((numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8]) * 8) % 10 == numbers[10];

			return kosul1 && kosul2 && kosul3;
		}
		return false;
	}

	private static int[] parcala(String nationalIdentity) {
		int[] numbers = new int[11];

		if (nationalIdentity == null || nationalIdentity.length() != 11) {
			return null;
		}

		for (int i = 0; i < 11; i++) {
			numbers[i] = Integer.parseInt(nationalIdentity.substring(i, (i + 1)));
		}
		return numbers;
	}

}
