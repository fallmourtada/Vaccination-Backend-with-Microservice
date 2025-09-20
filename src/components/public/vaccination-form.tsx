import React from 'react';
import { Card } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { useVaccinationsByEnfant, useCreateVaccination } from '@/services/vaccinations-hooks';
import type { VaccinationCreateDTO } from '@/types';

interface VaccinationFormProps {
  enfantId: string;
  onSuccess?: () => void;
}

/**
 * Exemple de composant utilisant les hooks de vaccination
 */
export default function VaccinationForm({ enfantId, onSuccess }: VaccinationFormProps) {
  const [vaccinId, setVaccinId] = React.useState('');
  const [dateVaccination, setDateVaccination] = React.useState(new Date().toISOString().split('T')[0]);
  const [centreVaccination, setCentreVaccination] = React.useState('');
  
  // Utilisation du hook pour obtenir les vaccinations existantes
  const { 
    data: vaccinations = [], // Initialiser avec un tableau vide pour éviter les erreurs
    isLoading, 
    error 
  } = useVaccinationsByEnfant(enfantId);
  
  // Utilisation du hook pour créer une nouvelle vaccination
  const { 
    mutate: createVaccination,
    isPending,
    isSuccess,
    isError,
    error: mutationError
  } = useCreateVaccination();

  // Gestion de la soumission du formulaire
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    const newVaccination: VaccinationCreateDTO = {
      enfantId,
      vaccinId,
      dateVaccination,
      centreVaccination,
      lotVaccin: '', // À compléter selon le formulaire
      professionnelSante: '', // À compléter selon le formulaire
      reactions: '',
      prochainRappel: null
    };
    
    // @ts-ignore - Le typage est correct mais TS ne le détecte pas correctement
    createVaccination(newVaccination, {
      onSuccess: () => {
        // Réinitialiser le formulaire
        setVaccinId('');
        setDateVaccination(new Date().toISOString().split('T')[0]);
        setCentreVaccination('');
        
        // Callback optionnel
        if (onSuccess) onSuccess();
      }
    });
  };

  if (isLoading) {
    return <div className="p-4">Chargement des vaccinations...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Erreur: {(error as Error).message}</div>;
  }

  return (
    <div className="space-y-6">
      <Card className="p-6">
        <h2 className="text-xl font-semibold mb-4">Enregistrer une nouvelle vaccination</h2>
        
        {isSuccess && (
          <div className="mb-4 p-2 bg-green-100 text-green-800 rounded">
            Vaccination enregistrée avec succès!
          </div>
        )}
        
        {isError && (
          <div className="mb-4 p-2 bg-red-100 text-red-800 rounded">
            Erreur: {(mutationError as Error)?.message || 'Une erreur est survenue lors de l\'enregistrement'}
          </div>
        )}
        
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="vaccinId" className="block text-sm font-medium mb-1">
              Vaccin
            </label>
            <select
              id="vaccinId"
              value={vaccinId}
              onChange={(e) => setVaccinId(e.target.value)}
              className="w-full p-2 border rounded"
              required
            >
              <option value="">Sélectionner un vaccin</option>
              {/* Options à remplir depuis l'API */}
              <option value="vaccin1">Vaccin 1</option>
              <option value="vaccin2">Vaccin 2</option>
            </select>
          </div>
          
          <div>
            <label htmlFor="dateVaccination" className="block text-sm font-medium mb-1">
              Date de vaccination
            </label>
            <input
              id="dateVaccination"
              type="date"
              value={dateVaccination}
              onChange={(e) => setDateVaccination(e.target.value)}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          
          <div>
            <label htmlFor="centreVaccination" className="block text-sm font-medium mb-1">
              Centre de vaccination
            </label>
            <input
              id="centreVaccination"
              type="text"
              value={centreVaccination}
              onChange={(e) => setCentreVaccination(e.target.value)}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          
          <Button type="submit" disabled={isPending} className="w-full">
            {isPending ? 'Enregistrement en cours...' : 'Enregistrer la vaccination'}
          </Button>
        </form>
      </Card>
      
      <Card className="p-6">
        <h2 className="text-xl font-semibold mb-4">Vaccinations précédentes</h2>
        
        {vaccinations && Array.isArray(vaccinations) && vaccinations.length > 0 ? (
          <ul className="space-y-2">
            {vaccinations.map((vaccination: any) => (
              <li key={vaccination.id} className="p-2 bg-gray-50 rounded">
                <div className="font-medium">{vaccination.vaccin?.nom || 'Vaccin non spécifié'}</div>
                <div className="text-sm text-gray-600">
                  Date: {new Date(vaccination.dateVaccination).toLocaleDateString()}
                </div>
                <div className="text-sm text-gray-600">
                  Centre: {vaccination.centreVaccination}
                </div>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">Aucune vaccination enregistrée pour cet enfant.</p>
        )}
      </Card>
    </div>
  );
}
