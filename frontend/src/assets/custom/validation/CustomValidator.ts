import {AbstractControl, ValidatorFn} from '@angular/forms';

export const emailOrNullValidator = (): ValidatorFn => {
    return (control: AbstractControl): { [key: string]: string } => {
        const result = /(^null$|^$|^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$)/i.test(control.value);
        // console.log(`emailValidator = ${result}`);
        return result === true ? null : {Error: 'Email không đúng định dạng.'};
    };
};
export const passwordValidator = (): ValidatorFn => {
    return (control: AbstractControl): { [key: string]: string } => {
        const result = /^[a-zA-Z0-9_@]{6,18}$/i.test(control.value);
        // console.log(`emailValidator = ${result}`);
        return result === true ? null : {Error: 'Mật khẩu không hợp lệ.'};
    };
};
export const phoneNumberValidator = (): ValidatorFn => {
    return (control: AbstractControl): { [key: string]: string } => {
        // tslint:disable-next-line:max-line-length
        const result = /^(05[5|8|9]|08[1|2|3|4|5|8|6|9]|03[2|3|4|5|6|7|8|9]|07[0|9|7|6|8]|09[0|2|1|4|3|6|7|8|9]|01[2|9])+([0-9]{7})\b$/i.test(control.value);
        // console.log(`emailValidator = ${result}`);
        return result === true ? null : {Error: 'Số điện thoại không đúng định dạng.'};
    };
};
