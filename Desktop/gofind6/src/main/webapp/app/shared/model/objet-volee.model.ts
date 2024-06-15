import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IObjetVolee {
  id?: number;
  type?: string | null;
  marque?: string | null;
  datevol?: dayjs.Dayjs | null;
  photoObjetContentType?: string | null;
  photoObjet?: string | null;
  numeroSerie?: string | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IObjetVolee> = {};
