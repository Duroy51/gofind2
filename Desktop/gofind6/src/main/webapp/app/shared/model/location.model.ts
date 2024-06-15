import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ILocation {
  id?: number;
  lieu?: string | null;
  prix?: number | null;
  description?: string | null;
  photoPiecesContentType?: string | null;
  photoPieces?: string | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ILocation> = {};
