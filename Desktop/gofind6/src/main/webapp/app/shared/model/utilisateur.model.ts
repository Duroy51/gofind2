import { IUser } from 'app/shared/model/user.model';

export interface IUtilisateur {
  id?: number;
  userName?: string | null;
  email?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUtilisateur> = {};
