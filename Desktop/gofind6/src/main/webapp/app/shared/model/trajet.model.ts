import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ITrajet {
  id?: number;
  date?: dayjs.Dayjs | null;
  heuredepart?: string | null;
  heurearrivee?: string | null;
  lieudepart?: string | null;
  lieuarrivee?: string | null;
  nombreplace?: number | null;
  prixplace?: number | null;
  imageVehiculeContentType?: string | null;
  imageVehicule?: string | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ITrajet> = {};
