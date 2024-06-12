export interface IUtilisateur {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  telephone?: string | null;
  password?: string | null;
}

export const defaultValue: Readonly<IUtilisateur> = {};
