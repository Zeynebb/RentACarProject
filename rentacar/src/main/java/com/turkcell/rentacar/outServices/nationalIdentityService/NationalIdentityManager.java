package com.turkcell.rentacar.outServices.nationalIdentityService;

import org.springframework.stereotype.Service;

@Service
public class NationalIdentityManager implements NationalIdentityService {

	@Override
	public boolean checkNationalIdentity(String nationalIdentity) {
		int[] numbers = split(nationalIdentity);

		if (numbers != null) {
			boolean condition1 = (numbers[0] + numbers[1] + numbers[2] + numbers[3] + numbers[4] + numbers[5]
					+ numbers[6] + numbers[7] + numbers[8] + numbers[9]) % 10 == numbers[10];
			boolean condition2 = (((numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8]) * 7)
					+ ((numbers[1] + numbers[3] + numbers[5] + numbers[7]) * 9)) % 10 == numbers[9];
			boolean condition3 = ((numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8]) * 8)
					% 10 == numbers[10];

			return condition1 && condition2 && condition3;
		}
		return false;
	}

	private static int[] split(String nationalIdentity) {
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
