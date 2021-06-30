import {ERole} from './erole.enum';

export class Account {
    id: number;
    accountName: string;
    fullName: string;
    dateOfBirth: string;
    avatar: string;
    position: string;
    candidateplace: string;
    status: string;
    role: ERole[];
    agency: string;
    token?: string;
    type: string;
}
